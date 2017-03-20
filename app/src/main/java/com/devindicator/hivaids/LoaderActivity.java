package com.devindicator.hivaids;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.devindicator.hivaids.database.DBQuery;
import com.devindicator.hivaids.models.User;

public class LoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        try {
            DBQuery dbQuery = new DBQuery(this);
            dbQuery.open();
            User user = dbQuery.getUser();
            if (null != user) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
