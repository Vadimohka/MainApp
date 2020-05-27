package com.application.presidentapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.application.presidentapplication.Network.NetworkStateReader;
import com.application.presidentapplication.R;

import java.io.File;

public class RSSPostActivity extends AppCompatActivity {
    private WebView webView;
    private String link;
    private ProgressBar progressBar;
    private int postIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_web_view);

        link = getIntent().getStringExtra("link");
        postIndex = getIntent().getIntExtra("position", 0);
        webView = findViewById(R.id.post_veb_view_holder);
        progressBar = findViewById(R.id.postProgressBar);
        openURL();
    }


    private void openURL() {
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
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
