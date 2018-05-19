package com.dn.store.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dn.store.R;
import com.dn.store.models.Category;
import com.dn.store.models.DataListener;
import com.dn.store.models.Product;
import com.dn.store.views.adapters.CategoriesAdapter;
import com.dn.store.views.adapters.ProductAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public abstract class FragmentListCategories extends Fragment {

    public static final String ARG_OBJECT = "ID_CATEGORIES";

    private int rootId;

    private DatabaseReference mDatabase;

    private FirebaseRecyclerAdapter<Category, CategoriesAdapter.CategoryItemHolder> mAdapter;

    private RecyclerView mRecycler;

    private GridLayoutManager mManager;

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_categories, container, false);

        progressBar = view.findViewById(R.id.progress_bar_cate);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRecycler = view.findViewById(R.id.list_categories);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mManager = new GridLayoutManager(getActivity(), 3);
        mRecycler.setLayoutManager(mManager);

        DataListener dataListener = new DataListener() {
            @Override
            public void onChanged() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);
            }
        };

        Query postsQuery = getQuery(mDatabase);
        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(postsQuery, Category.class)
                .build();
        mAdapter = new CategoriesAdapter(options, getActivity(), dataListener, getRootId());
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
            progressBar.setVisibility(View.GONE);
        }
    }

    public abstract Query getQuery(DatabaseReference mDatabase);

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }
}
