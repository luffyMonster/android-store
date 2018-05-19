package com.dn.store.views.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dn.store.R;
import com.dn.store.utils.Utils;
import com.dn.store.views.fragments.AccountFragment;
import com.dn.store.views.fragments.CategoryFragment;
import com.dn.store.views.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    boolean firstRun = true;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Utils.registerTransaction(getFragmentManager(), R.id.fragment_content, new HomeFragment());
                    return true;
                case R.id.navigation_categories:
                    Utils.registerTransaction(getFragmentManager(), R.id.fragment_content, new CategoryFragment());
                    return true;
                case R.id.navigation_account:
                    Utils.registerTransaction(getFragmentManager(), R.id.fragment_content, new AccountFragment());
                    return true;
                case R.id.navigation_cart:
                    Intent intent = new Intent(MainActivity.this, GioHangActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (firstRun) {
            Utils.registerTransaction(getFragmentManager(), R.id.fragment_content, new HomeFragment());
            firstRun = false;
        }

    }
}
