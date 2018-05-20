package com.dn.store.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.widget.Toast;

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

//    public static boolean haveNetworkConnection(Context context) {
//        boolean haveConnectedWifi = false;
//        boolean haveConnectedMobile = false;
//
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
//        for (NetworkInfo ni : netInfo) {
//            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
//                if (ni.isConnected())
//                    haveConnectedWifi = true;
//            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
//                if (ni.isConnected())
//                    haveConnectedMobile = true;
//        }
//        return haveConnectedWifi || haveConnectedMobile;
//    }


    public static void showToastShort(Context context, String thongbao){
        Toast.makeText(context, thongbao,Toast.LENGTH_SHORT).show();
    }
}
