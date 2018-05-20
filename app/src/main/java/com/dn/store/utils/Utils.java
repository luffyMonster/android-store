package com.dn.store.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.widget.Toast;

import com.dn.store.models.Cart;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public static void writeNewCart(String userId, Cart cart, OnSuccessListener slistener, OnFailureListener flistener) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("carts").child(userId).push().setValue(cart).addOnSuccessListener(slistener).addOnFailureListener(flistener);
    }


    public static void showToastShort(Context context, String thongbao){
        Toast.makeText(context, thongbao,Toast.LENGTH_SHORT).show();
    }
}
