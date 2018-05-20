package com.dn.store.views.fragments;

import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MeVaBeFragment extends FragmentListCategories {

    @Override
    public Query getQuery(DatabaseReference mDatabase) {
        setRootId("2");
        return mDatabase.child("categories").orderByChild("root_id").equalTo(getRootId());
    }

}
