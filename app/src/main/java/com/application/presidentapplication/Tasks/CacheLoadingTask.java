package com.application.presidentapplication.Tasks;

import android.app.Activity;
import android.os.AsyncTask;


import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.application.presidentapplication.Adapters.PostAdapter;
import com.application.presidentapplication.DatabaseWork.RSSDatabase;
import com.application.presidentapplication.Models.RSSPost;
import java.util.ArrayList;

public class CacheLoadingTask  extends AsyncTask<Void, Void, Boolean> {

    private final RSSDatabase db;
    private final SwipeRefreshLayout mSwipeLayout;
    private ArrayList<RSSPost> loaded_posts;
    private RecyclerView mRecyclerView;
    private final Activity context;

    public CacheLoadingTask(RSSDatabase db, Activity context, RecyclerView recyclerView, SwipeRefreshLayout mSwipeLayout) {
        this.db = db;
        this.mSwipeLayout = mSwipeLayout;
        this.context = context;
        this.mRecyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute() {
        mSwipeLayout.setRefreshing(true);
    }

    @Override
    protected Boolean doInBackground(Void...voids) {
        try {
            loaded_posts = new ArrayList<>();
            loaded_posts.addAll(db.getRSSPostDao().getAll());
            return loaded_posts.size() > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        mSwipeLayout.setRefreshing(false);
        if (success) mRecyclerView.setAdapter(new PostAdapter(context, loaded_posts));
    }


}
