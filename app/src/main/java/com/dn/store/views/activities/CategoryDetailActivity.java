package com.dn.store.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.dn.store.R;

public class CategoryDetailActivity extends AppCompatActivity {
    public static String ROOT_ID = "ROOT_ID";
    int rootId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        rootId = getIntent().getIntExtra(ROOT_ID, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return false;
    }
}
