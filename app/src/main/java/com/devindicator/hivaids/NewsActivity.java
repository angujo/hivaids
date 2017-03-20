package com.devindicator.hivaids;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.devindicator.hivaids.database.DBQuery;
import com.devindicator.hivaids.models.Commons;
import com.devindicator.hivaids.models.Helper;
import com.devindicator.hivaids.models.NewsAdapter;
import com.devindicator.hivaids.models.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    private NewsAdapter newsAdapter;
    private ArrayList<NewsItem> newsItems;
    private ListView newsListView;
    private DBQuery dbQuery;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Latest News & Events");

        dbQuery = new DBQuery(this);
        dbQuery.open();
        newsItems = dbQuery.getNewsItems();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        newsListView = (ListView) findViewById(R.id.lvNews);
       /* newsItems = new ArrayList<>();
        NewsItem newsItem;
        for (int i = 0; i < 12; i++) {
            newsItem = new NewsItem();
            newsItem.setTitle("New Vaccine for all hospitals!");
            newsItem.setSource("AFP Reuters");
            newsItem.setDateTime(Commons.getCurrentDate() + " " + Commons.getCurrentTime());
            newsItems.add(newsItem);
        }*/
        newsAdapter = new NewsAdapter(getApplicationContext(), newsItems);

        newsListView.setAdapter(newsAdapter);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity((new Intent(getApplicationContext(), LinkViewActivity.class).putExtra(LinkViewActivity.ARGUMENT_ITEM, newsItems.get(i).getId())));
            }
        });
        if (Helper.isNetworkAvailable(this)) {
            progressDialog.show();
            fetch();
        } else {
            closeNow();
        }
    }

    private void closeNow() {
        if (0 >= newsItems.size()) {
            Toast.makeText(this, "No News Items yet!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void fetch() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Helper.URL_NEWS + "?link=yes",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPONSE", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("status")) {
                                newsItems.clear();
                                JSONArray jsonArray = jsonObject.getJSONArray("response");
                                Log.d("RESPONSE_COUNT", jsonArray.length() + "");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    NewsItem newsItem = new NewsItem();
                                    JSONObject jsonNews = jsonArray.getJSONObject(i);
                                    newsItem.setDateTime(jsonNews.getString("dated"));
                                    newsItem.setThumbUrl(jsonNews.getString("thumbnail"));
                                    newsItem.setSource(jsonNews.getString("source"));
                                    newsItem.setTitle(jsonNews.getString("title"));
                                    newsItem.setId(jsonNews.getInt("id"));
                                    newsItem.setLink(jsonNews.getString("link"));
                                    if (dbQuery.setNewsItem(newsItem)) {
                                        newsItems.add(newsItem);
                                    }
                                }
                                newsAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onBackPressed() {
        finish();
    }
}
