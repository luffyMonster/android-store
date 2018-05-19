package com.dn.store.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class Utils {
    public static void registerTransaction(FragmentManager manager, int container, Fragment fragment, String tag){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(container, fragment, tag);
        transaction.commit();
    }
    public static void registerTransaction(FragmentManager manager, int container, Fragment fragment){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(container, fragment).addToBackStack(null);
        transaction.commit();
    }
}
