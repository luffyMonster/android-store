package com.dn.store;

import android.app.Application;
import android.app.Fragment;

import com.dn.store.views.adapters.ProductAdapter;
import com.dn.store.views.fragments.LatestProductListFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public final class App extends Application {

    private static App self;


    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
    }

    public static App getInstance(){
        return self;
    }
}
