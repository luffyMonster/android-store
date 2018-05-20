package com.dn.store.models;

public interface CartListener {
    void onPlus(long price);
    void onMinus(long price);
}
