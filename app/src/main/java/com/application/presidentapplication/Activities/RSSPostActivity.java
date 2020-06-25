package com.application.presidentapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.application.presidentapplication.Network.NetworkStateReader;
import com.application.presidentapplication.R;

import java.io.File;
import java.util.Objects;

public class RSSPostActivity extends AppCompatActivity {
    private WebView webView;
    private String link;
    private int postIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_web_view);

        link = getIntent().getStringExtra("link");
        postIndex = getIntent().getIntExtra("position", 0);
        webView = findViewById(R.id.post_veb_view_holder);
        openURL();
    }


    private void openURL() {
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return !Objects.equals(Uri.parse(url).getHost(), "www.belta.by");
            }
        });
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        if (NetworkStateReader.getConnectivityStatusString(this).equals(this.getResources().getString(R.string.no_internet)))
            webView.loadUrl("file://" + getFilesDir().getAbsolutePath() + File.separator + postIndex + ".mht");
        else
            webView.loadUrl(link);
    }
}
