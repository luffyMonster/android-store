package com.dn.store.views.fragments;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class ThoiTrangNamFragment extends FragmentListCategories {

    @Override
    public Query getQuery(DatabaseReference mDatabase) {
        setRootId("0");
        return mDatabase.child("categories").orderByChild("root_id").equalTo(getRootId());
    }

}
