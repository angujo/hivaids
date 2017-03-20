package com.devindicator.hivaids;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.devindicator.hivaids.database.DBQuery;
import com.devindicator.hivaids.models.Article;
import com.devindicator.hivaids.models.Helper;

import org.json.JSONObject;

public class ArticleActivity extends AppCompatActivity {
    public static final String ARGUMENT_ARTICLE = "ARG_ART";
    private DBQuery dbQuery;
    private Article article;
    private TextView tvArticle;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.pbLoading);
        tvArticle = (TextView) findViewById(R.id.tvContent);

        dbQuery = new DBQuery(this);
        dbQuery.open();

        article = dbQuery.getArticle(getIntent().getExtras().getInt(ARGUMENT_ARTICLE, 0));
        setTitle(article.getTitle());
        tvArticle.setText((null == article.getContent() || 0 >= article.getContent().length() ? article.getSummary() : article.getContent()));
        if (Helper.isNetworkAvailable(this)) {
            fetch();
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void fetch() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Helper.URL_ARTICLE + "?article=yes",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CAT_RESP", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("status")) {
                                Article cArticle = new Article();
                                JSONObject categoryObject = jsonObject.getJSONObject("response");
                                cArticle.setId(categoryObject.getInt("id"));
                                cArticle.setTitle(categoryObject.getString("title"));
                                cArticle.setSummary(categoryObject.getString("summary"));
                                cArticle.setContent(categoryObject.getString("content"));
                                cArticle.setCategory(categoryObject.getInt("category"));
                                if (dbQuery.setArticle(cArticle)) {
                                    article = cArticle;
                                }
                            }
                            closeNow();
                        } catch (Exception e) {
                            e.printStackTrace();
                            closeNow();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        closeNow();
                    }
                });
        requestQueue.add(stringRequest);
    }

    private void closeNow() {
        progressBar.setVisibility(View.GONE);
        if (null == article) {
            finish();
        } else {
            setTitle(article.getTitle());
            tvArticle.setText(article.getContent());
        }
    }
}
