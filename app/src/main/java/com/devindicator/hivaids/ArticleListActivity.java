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
import com.devindicator.hivaids.models.Article;
import com.devindicator.hivaids.models.ArticlesAdapter;
import com.devindicator.hivaids.models.Category;
import com.devindicator.hivaids.models.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArticleListActivity extends AppCompatActivity {
    public static final String ARGUMENT_CATEGORY = "ARG_CAT";
    private DBQuery dbQuery;
    private Category category;
    private ArrayList<Article> articleArrayList;
    private ArticlesAdapter articlesAdapter;
    private ListView lvArticles;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading articles...");
        lvArticles = (ListView) findViewById(R.id.lvArticles);

        dbQuery = new DBQuery(this);
        dbQuery.open();
        category = dbQuery.getCategory(getIntent().getExtras().getInt(ARGUMENT_CATEGORY));

        setTitle(category.getTitle());
        articleArrayList = dbQuery.getArticles(category.getId());

        articlesAdapter = new ArticlesAdapter(this, articleArrayList);
        lvArticles.setAdapter(articlesAdapter);
        lvArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity((new Intent(getApplicationContext(), ArticleActivity.class)).putExtra(ArticleActivity.ARGUMENT_ARTICLE, articleArrayList.get(i).getId()));
            }
        });
        if (Helper.isNetworkAvailable(this)) {
            fetch();
        }
    }

    public void fetch() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Helper.URL_ARTICLES + "?articles=yes",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CAT_RESP", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("status")) {
                                articleArrayList.clear();
                                JSONArray jsonArray = jsonObject.getJSONArray("response");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Article article = new Article();
                                    JSONObject categoryObject = jsonArray.getJSONObject(i);
                                    article.setId(categoryObject.getInt("id"));
                                    article.setTitle(categoryObject.getString("title"));
                                    article.setDated(categoryObject.getString("dated"));
                                    article.setCategory(categoryObject.getInt("category"));
                                    article.setSummary(categoryObject.getString("summary"));
                                    article.setThumbnailURL(categoryObject.getString("thumbnail"));
                                    if (dbQuery.setArticle(article)) {
                                        articleArrayList.add(article);
                                    }
                                }
                                articlesAdapter.notifyDataSetChanged();
                            } else {
                                closeNow();
                            }
                            progressDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            closeNow();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressDialog.dismiss();
                        closeNow();
                    }
                });
        requestQueue.add(stringRequest);
    }

    private void closeNow() {
        if (0 >= articleArrayList.size()) {
            Toast.makeText(this, "No articles to show!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
