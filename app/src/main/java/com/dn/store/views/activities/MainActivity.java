package com.dn.store.views.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dn.store.R;
import com.dn.store.utils.Utils;
import com.dn.store.views.fragments.AccountFragment;
import com.dn.store.views.fragments.CategoryFragment;
import com.dn.store.views.fragments.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

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
                    mAuth = FirebaseAuth.getInstance();
                    if(mAuth.getCurrentUser() == null) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return true;
                    } else {
                        Toast.makeText(MainActivity.this,"Đăng nhập thành công rồi, gọi activity qlUser nhé", Toast.LENGTH_SHORT).show();
                    }
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }



    @Override
    protected void onResume() {
        super.onResume();
        Utils.registerTransaction(getFragmentManager(), R.id.fragment_content, new HomeFragment());
    }
}
