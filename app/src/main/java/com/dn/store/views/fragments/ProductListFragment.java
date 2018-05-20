package com.dn.store.views.fragments;

import android.app.Fragment;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dn.store.App;
import com.dn.store.R;
import com.dn.store.models.Product;
import com.dn.store.views.adapters.ProductAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public abstract class ProductListFragment extends Fragment {
    private static final String TAG = "ProductListFragment";

    private DatabaseReference mDatabase;

    private FirebaseRecyclerAdapter<Product, ProductAdapter.ProductItemViewHolder> mAdapter;

    private RecyclerView mRecycler;

    private GridLayoutManager mManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_product_list, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRecycler = rootView.findViewById(R.id.product_list);
        mRecycler.setHasFixedSize(false);
        mRecycler.setNestedScrollingEnabled(false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mManager = new GridLayoutManager(getActivity(), 2);
        mRecycler.setLayoutManager(mManager);

        Query postsQuery = getQuery(mDatabase);
        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(postsQuery, Product.class)
                .build();
        mAdapter = new ProductAdapter(options, getActivity(), null, 0);
        mRecycler.setAdapter(mAdapter);

    }


    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }


    protected abstract Query getQuery(DatabaseReference mDatabase);

}
