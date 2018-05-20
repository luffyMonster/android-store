package com.dn.store.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dn.store.R;
import com.dn.store.models.DataListener;
import com.dn.store.models.Product;
import com.dn.store.views.adapters.ProductAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Toolbar mToolbar;
    private RecyclerView mRecycler;
    private SearchView searchView;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Product, ProductAdapter.ProductItemViewHolder> mAdapter;
    private GridLayoutManager mManager;

    private ProgressBar progressBar;
    TextView txtNoItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        txtNoItem = findViewById(R.id.txt_no_item);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecycler = findViewById(R.id.recyclerSearch);
        mManager = new GridLayoutManager(this, 2);
        mRecycler.setLayoutManager(mManager);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRecycler.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);

        MenuItem itSearch = menu.findItem(R.id.itSearch);
        searchView = (android.support.v7.widget.SearchView) itSearch.getActionView();
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cart_action:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, GioHangActivity.class);
                    startActivity(intent);
                }
                return true;
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        firebaseProductSearch(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void firebaseProductSearch(String query) {
        Log.d(this.getClass().getName(), "Start search: "+query);
        if (mAdapter != null){
            mAdapter.stopListening();
        }
        Query prodQuery = getQuery(mDatabase, query);
        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(prodQuery, Product.class)
                .build();

        mAdapter = new ProductAdapter(options, this, new DataListener() {
            @Override
            public void onChanged() {
                progressBar.setVisibility(View.GONE);
                txtNoItem.setVisibility(View.GONE);
                if (mAdapter.getItemCount() == 0){
                    txtNoItem.setText(getResources().getText(R.string.search_not_found));
                    txtNoItem.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError() {
                txtNoItem.setText("Fail to load data");
                txtNoItem.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }, 0);
        mRecycler.setAdapter(mAdapter);
        mAdapter.startListening();
        progressBar.setVisibility(View.VISIBLE);
        searchView.setIconified(false);
    }

    private Query getQuery(DatabaseReference mDatabase, String q) {
        return mDatabase.child("products").orderByChild("name")
                .startAt(q)
                .endAt(q + "\uf8ff") //%
                .limitToFirst(30);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdapter != null){
            mAdapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null){
            mAdapter.startListening();
        }
    }
}
