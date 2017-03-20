package com.devindicator.hivaids;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.devindicator.hivaids.database.DBQuery;
import com.devindicator.hivaids.models.Helper;
import com.devindicator.hivaids.models.Page;

import org.json.JSONException;
import org.json.JSONObject;

public class AbcActivity extends AppCompatActivity {
    ProgressBar progressBar;
    DBQuery dbQuery;
    TextView tvContent;
    Page page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Loading");
        progressBar = (ProgressBar) findViewById(R.id.pbLoading);
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvContent.setText("");

        dbQuery = new DBQuery(this);
        dbQuery.open();
        page = dbQuery.getPage(Helper.PAGE_ABC);
        if (null == page && !Helper.isNetworkAvailable(this)) {
            Toast.makeText(this, "Unable to load Content. Exiting...", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        if (null != page) {
            setTitle(page.getTitle());
            tvContent.setText(page.getContent());
        }
        if (Helper.isNetworkAvailable(this)) {
            fetchPage();
        }
    }

    private void closePage(Page iPage) {
        if (page == null && null == iPage) {
            this.finish();
        } else if (iPage != null) {
            this.setTitle(iPage.getTitle());
            tvContent.setText(iPage.getContent());
        }
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    private void fetchPage() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Helper.URL_PAGE + "?page=" + Helper.PAGE_ABC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Response", response);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("status")) {
                                JSONObject jsonResponse = jsonObject.getJSONObject("response");
                                Page iPage = new Page();
                                iPage.setType(jsonResponse.getString("type"));
                                iPage.setTitle(jsonResponse.getString("title"));
                                iPage.setContent(jsonResponse.getString("content"));
                                iPage.setId(jsonResponse.getInt("id"));
                                dbQuery.setPage(iPage);
                                closePage(iPage);
                            } else {
                                Toast.makeText(AbcActivity.this, "Unable to parse result!", Toast.LENGTH_SHORT).show();
                                closePage(null);
                            }
                        } catch (Exception e) {
                            Toast.makeText(AbcActivity.this, "Invalid Response!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            closePage(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        closePage(null);
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    public void onBackPressed() {
        finish();
    }
}
