package com.application.presidentapplication.Tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.Gravity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.application.presidentapplication.Adapters.PostAdapter;
import com.application.presidentapplication.DatabaseWork.RSSDatabase;
import com.application.presidentapplication.Models.RSSPost;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RSSFeedControl extends AsyncTask<Void, Integer, Boolean> {
    private  Context context;
    private final String address;
    private SwipeRefreshLayout mSwipeLayout;
    private ArrayList<RSSPost> loaded_posts;
    private RecyclerView mRecyclerView;
    private ArrayList<WebView> webViews = new ArrayList<>();
    private final RSSDatabase db;

    public RSSFeedControl(Context context, String address, SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView,
                          RSSDatabase db) {
        this.context = context;
        this.address = address;
        this.mSwipeLayout = swipeRefreshLayout;
        this.mRecyclerView = recyclerView;
        this.db = db;
    }

    @Override
    protected void onPreExecute() {
        mSwipeLayout.setRefreshing(true);
    }

    @Override
    protected void onProgressUpdate(Integer...integers) {
        super.onProgressUpdate(integers);
        final int i = integers[0];
        WebView view = new WebView(context);
        view.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                view.saveWebArchive(context.getFilesDir().getAbsolutePath() + File.separator + i + ".mht");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return !Objects.equals(Uri.parse(url).getHost(), "www.belta.by");
            }
        });
        view.loadUrl(loaded_posts.get(i).getLink());
        webViews.add(view);
    }


    @Override
    protected Boolean doInBackground(Void...voids) {
        try {
            ProcessXml(tryConnection(address));
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        mSwipeLayout.setRefreshing(false);
        if (success) mRecyclerView.setAdapter(new PostAdapter(context, loaded_posts));

    }

    private Document tryConnection(String address) {
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            return builder.parse(inputStream);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void ProcessXml(Document document) {
        ArrayList<RSSPost> posts = new ArrayList<>();
        if (document != null) {
            Element root = document.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            if (channel == null)
                channel = root.getChildNodes().item(0);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node currentchild = items.item(i);
                if (currentchild.getNodeName().equalsIgnoreCase("item")) {
                    RSSPost rssPost = new RSSPost();
                    NodeList itemchilds = currentchild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node current = itemchilds.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")) {
                            String title = current.getTextContent();
                            rssPost.setTitle(title.trim());
                        }
                        else if (current.getNodeName().equalsIgnoreCase("description")) {
                            String[] s = current.getTextContent().split(">");
                            StringBuilder desc = new StringBuilder();
                            for (String value : s)
                                if (value.charAt(0) != '<' && value.length() > 6 && !value.contains("<img")) {
                                    String[] subs = value.split("<");
                                    for (String subss : subs)
                                        if (subss.length() > 3 && !subss.contains("a href") && !subss.contains("br clear"))
                                            desc.append(subss);
                                }
                            rssPost.setContent(desc.toString());
                        }
                        else if (current.getNodeName().equalsIgnoreCase("link")) {
                            rssPost.setLink(current.getTextContent());
                        }
                        else if (current.getNodeName().equalsIgnoreCase("media:thumbnail") ||
                                current.getNodeName().equalsIgnoreCase("enclosure")) {
                            rssPost.setImage(current.getAttributes().item(0).getTextContent());
                        }
                    }
                    posts.add(rssPost);
                }
            }

            loaded_posts = posts;
            db.getRSSPostDao().deleteAll();
            int count = 10;
            if (posts.size() < count) count = posts.size();
            for (int k = 0; k < count; k++) {
                RSSPost post = posts.get(k);
                String img= post.getImage();
                try {
                    URL url = new URL(img);
                    Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                    byte[] b = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    post.setCachedImage(encodedImage);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                db.getRSSPostDao().insert(post);
                publishProgress(posts.indexOf(post));
            }
        }
    }


}
