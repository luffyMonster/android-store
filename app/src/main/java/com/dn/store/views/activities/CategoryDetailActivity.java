package com.dn.store.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.dn.store.R;
import com.dn.store.models.Category;
import com.dn.store.models.DataListener;
import com.dn.store.models.Product;
import com.dn.store.views.adapters.ProductAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CategoryDetailActivity extends AppCompatActivity implements DataListener{
    public static String CATEGORY_KEY = "CATEGORY_KEY";
    String category_key;

    Toolbar mToolbar;
    DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Product, ProductAdapter.ProductItemViewHolder> mAdapter;
    private RecyclerView.LayoutManager mManager;
    private RecyclerView mRecycler;
    private boolean isGrid = true;
    private ProgressBar progressBar;

    Button btnChangeView;
    FirebaseRecyclerOptions<Product> options = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        category_key = getIntent().getStringExtra(CATEGORY_KEY);
        progressBar = findViewById(R.id.progress_bar);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecycler = findViewById(R.id.recycleViewHienThiSanPhamTheoDanhMuc);


        //Get category detail
        mDatabase.child("categories").child(category_key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Category category = dataSnapshot.getValue(Category.class);
                mToolbar.setTitle(category.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Get product in categories
        Query query =  mDatabase.child("products")
                .orderByChild("categories/" + category_key)
                .equalTo(true);
        options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();

        setLayoutManager(options);


    }

    private void setLayoutManager( FirebaseRecyclerOptions<Product> options) {
        if (mAdapter != null){
            mAdapter.stopListening();
        }
        if (isGrid) {
            mManager = new GridLayoutManager(this, 2);
            mAdapter = new ProductAdapter(options, this, this, R.layout.product_item);
        } else {
            mManager = new LinearLayoutManager(this);
            mAdapter = new ProductAdapter(options, this, this, R.layout.product_item_linear);
        }
        mRecycler.setLayoutManager(mManager);
        mRecycler.setAdapter(mAdapter);
        mAdapter.startListening();
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_categories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_view:
                isGrid = !isGrid;
                if (isGrid) {
                    item.setIcon(R.drawable.ic_view_list_black_24dp);
                } else {
                    item.setIcon(R.drawable.ic_view_module_black_24dp);
                }
                setLayoutManager(options);
                return true;
        }
        return false;
    }

    @Override
    public void onChanged() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError() {
        progressBar.setVisibility(View.GONE);
    }
}
