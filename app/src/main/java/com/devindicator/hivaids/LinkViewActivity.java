package com.devindicator.hivaids;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.devindicator.hivaids.database.DBQuery;
import com.devindicator.hivaids.models.NewsItem;

public class LinkViewActivity extends AppCompatActivity {
    public static final String ARGUMENT_ITEM = "ARG_NEWS_ITEM";
    private NewsItem newsItem;
    private DBQuery dbQuery;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("News & Events page");

        dbQuery = new DBQuery(this);
        dbQuery.open();
        newsItem = dbQuery.getNewsItem(getIntent().getExtras().getInt(ARGUMENT_ITEM));

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.wvProgress);
        WebView webView = (WebView) findViewById(R.id.wvPage);

        setTitle(newsItem.getTitle());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                if (progressBar != null && progressBar.isShown()) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            public void onPageStarted(WebView webView1, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), "Error: " + description + " " + failingUrl, Toast.LENGTH_LONG).show();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Make the bar disappear after URL is loaded, and changes
                // string to Loading...
                // MyActivity.setTitle("Loading...");
                progressBar.setProgress(progress);
            }
        });
        //webView.loadUrl("https://www.aids.gov/hiv-aids-basics/hiv-aids-101/what-is-hiv-aids/");
        webView.loadUrl(newsItem.getLink());
    }

    public void onBackPressed() {
        finish();
    }
}
