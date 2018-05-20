package com.dn.store.views.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dn.store.R;
import com.dn.store.models.Product;
import com.dn.store.utils.Utils;
import com.dn.store.views.fragments.AccountFragment;
import com.dn.store.views.fragments.CategoryFragment;
import com.dn.store.views.fragments.HomeFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    boolean firstRun = true;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Utils.registerTransaction(getFragmentManager(), R.id.fragment_content, new HomeFragment(), HomeFragment.class.getName());
                    return true;
                case R.id.navigation_categories:
                    Utils.registerTransaction(getFragmentManager(), R.id.fragment_content, new CategoryFragment(), CategoryFragment.class.getName());
                    return true;
                case R.id.navigation_account:
                    Utils.registerTransaction(getFragmentManager(), R.id.fragment_content, new AccountFragment(), AccountFragment.class.getName());
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

//    private void writeNewPost(String userId) {
//        // Create new post at /user-posts/$userid/$postid and at
//        // /posts/$postid simultaneously
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        String key = mDatabase.child("posts").push().getKey();
//        HashMap<String, Boolean> h = new HashMap<String, Boolean>();
//        h.put("1", true);
//        Product post = new Product("name", null, 1f, null, h ,1f, 1, new Date().getTime());
//        Map<String, Object> postValues = post.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/posts/" + key, postValues);
//        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
//
//        mDatabase.updateChildren(childUpdates);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if (firstRun) {
            Utils.registerTransaction(getFragmentManager(), R.id.fragment_content, new HomeFragment());
            firstRun = false;
        }

    }
}
