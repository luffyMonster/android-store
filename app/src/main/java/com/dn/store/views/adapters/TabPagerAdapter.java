package com.dn.store.views.adapters;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dn.store.views.fragments.FragmentListCategories;
import com.dn.store.views.fragments.MeVaBeFragment;
import com.dn.store.views.fragments.ThoiTrangNamFragment;
import com.dn.store.views.fragments.ThoiTrangNuFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    int tabQty;

    public TabPagerAdapter(FragmentManager fm, int tabQty) {
        super(fm);
        this.tabQty = tabQty;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ThoiTrangNamFragment();
                break;
            case 1:
                fragment = new ThoiTrangNuFragment();
                break;
            case 2:
                fragment = new MeVaBeFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabQty;
    }
}
