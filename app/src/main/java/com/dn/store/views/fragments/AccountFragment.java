package com.dn.store.views.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dn.store.R;
import com.dn.store.views.activities.GioHangActivity;
import com.dn.store.views.activities.MainActivity;
import com.dn.store.views.activities.SearchActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment{
    Toolbar toolbar;
    FirebaseAuth mAuth;
    View login;
    View logout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        mAuth = FirebaseAuth.getInstance();
        toolbar = view.findViewById(R.id.toolbar);

        login = view.findViewById(R.id.login);
        logout = view.findViewById(R.id.logout);

        if (mAuth.getCurrentUser() != null){
            toolbar.setSubtitle(getString(R.string.welcome) + " " + mAuth.getCurrentUser().getEmail());
            login.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
        } else {
            logout.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent); // Launch the HomescreenActivity
                getActivity().finish();         // Close down the SettingsActivity
            }
        });

        return  view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
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

}
