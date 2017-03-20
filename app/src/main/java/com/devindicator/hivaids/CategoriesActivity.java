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
import com.devindicator.hivaids.models.Category;
import com.devindicator.hivaids.models.CategoryAdapter;
import com.devindicator.hivaids.models.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {
    private ArrayList<Category> categories;
    private CategoryAdapter categoryAdapter;
    private ListView categoryList;
    private DBQuery dbQuery;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Categories");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Categories...");
        dbQuery = new DBQuery(this);
        dbQuery.open();

        categoryList = (ListView) findViewById(R.id.lvCategories);
        categories = dbQuery.getCategories();
       /* for (int i = 0; i < 20; i++) {
            categories.add(new Category("Category 101", "Desc...", i, 200));
        }*/
        categoryAdapter = new CategoryAdapter(categories, getApplicationContext());

        categoryList.setAdapter(categoryAdapter);
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity((new Intent(CategoriesActivity.this, ArticleListActivity.class)).putExtra(ArticleListActivity.ARGUMENT_CATEGORY, categories.get(i).getId()));
            }
        });
        if (Helper.isNetworkAvailable(this)) {
            fetch();
        } else {
            closeNow();
        }
    }

    private void closeNow() {
        if (0 >= categories.size()) {
            Toast.makeText(this, "No categories to show!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void onBackPressed() {
        finish();
    }

    public void fetch() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Helper.URL_CATEGORIES + "?categories=yes",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CAT_RESP", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("status")) {
                                categories.clear();
                                JSONArray jsonArray = jsonObject.getJSONArray("response");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Category category = new Category();
                                    JSONObject categoryObject = jsonArray.getJSONObject(i);
                                    category.setId(categoryObject.getInt("id"));
                                    category.setTitle(categoryObject.getString("title"));
                                    category.setDescription(categoryObject.getString("description"));
                                    if (dbQuery.setCategory(category)) {
                                        categories.add(category);
                                    }
                                }
                                categoryAdapter.notifyDataSetChanged();
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
}
