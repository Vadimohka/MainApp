package com.application.presidentapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.application.presidentapplication.Network.NetworkStateReader;
import com.application.presidentapplication.R;

import java.util.Objects;

public class ErrorActivity extends AppCompatActivity {

    private WebView webView;
    private String link = "https://docs.google.com/forms/d/e/1FAIpQLSeWItE1wMkX4w3hSw8LSWtUmyGzPpypJkDbYi-epUbhWaDGuA/viewform";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_web_view);
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
                return !Objects.equals(Uri.parse(url).getHost(), "https://docs.google.com/forms/d/e/1FAIpQLSeWItE1wMkX4w3hSw8LSWtUmyGzPpypJkDbYi-epUbhWaDGuA/viewform");
            }
        });
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        if (NetworkStateReader.getConnectivityStatusString(this).equals(this.getResources().getString(R.string.no_internet)))
            Toast.makeText(this, this.getResources().getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
        else
            webView.loadUrl(link);
    }
}