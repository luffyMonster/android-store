package com.dn.store.views.fragments;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class LatestProductListFragment extends ProductListFragment {
    @Override
    protected Query getQuery(DatabaseReference mDatabase) {
        return mDatabase.child("products").orderByChild("createdDate").limitToFirst(10);
    }
}
