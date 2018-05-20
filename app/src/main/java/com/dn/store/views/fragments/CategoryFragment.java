package com.dn.store.views.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.dn.store.R;
import com.dn.store.views.activities.GioHangActivity;
import com.dn.store.views.activities.MainActivity;
import com.dn.store.views.activities.SearchActivity;
import com.dn.store.views.adapters.TabPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    MainActivity activity;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager pager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_category, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_caterory_fragment);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        tabLayout = view.findViewById(R.id.tab_layout);
        pager = view.findViewById(R.id.pager);

        for (String tabName:getTabs()){
            tabLayout.addTab(tabLayout.newTab().setText(tabName));
        }

        final TabPagerAdapter pagerAdapter = new TabPagerAdapter( activity.getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
             @Override
             public void onTabSelected(TabLayout.Tab tab) {
                 pager.setCurrentItem(tab.getPosition());
                 toolbar.setTitle(tab.getText());
             }

             @Override
             public void onTabUnselected(TabLayout.Tab tab) {

             }

             @Override
             public void onTabReselected(TabLayout.Tab tab) {

             }
         });

        return view;
    }

    private String[] getTabs(){
        return  new String[]{"Thời trang nam", "Thời trang nữ", "Hàng Mẹ & Bé, Đồ chơi"};
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }
}
