package com.dn.store.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dn.store.R;
import com.dn.store.views.widgets.NotifiableViewFlipper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdsBlockFragment extends Fragment{


    private LayoutInflater inflater;
    private RadioGroup radioGroup;
    private NotifiableViewFlipper viewFlipper;
    private List<ImageView> imageViews = new ArrayList<>();
    private List<RadioButton> radioButtons = new ArrayList<>();

    private DatabaseReference mDatabase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.ads_block, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        viewFlipper = view.findViewById(R.id.ads_viewflipper);
        radioGroup = view.findViewById(R.id.ads_radio_group);


        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int id = 0;
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    String url = d.getValue(String.class);
                    createImageViews(url);
                    createRadios(id);
                    if (id == 0){
                        radioButtons.get(0).setChecked(true);
                    }
                    id++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.child("ads").limitToFirst(5).addListenerForSingleValueEvent(listener);


        viewFlipper.setOnFlipListener(adFlipListener);

        return view;
    }

    private void createImageViews(String res){
        ImageView imageView = (ImageView) inflater.inflate(R.layout.image_view_ads, null);
        Picasso.get().load(res).into(imageView);
        imageViews.add(imageView);
        viewFlipper.addView(imageView);
    }

    private void createRadios(int id){
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.radio_button_ads, null);
            radioButton.setId(id);
            radioGroup.addView(radioButton);
            radioButtons.add(radioButton);
    }

    private NotifiableViewFlipper.OnFlipListener adFlipListener = new NotifiableViewFlipper.OnFlipListener() {

        @Override
        public void onShowPrevious(NotifiableViewFlipper flipper) {
            if (flipper.getDisplayedChild() < radioButtons.size()) {
                radioGroup.check(radioButtons.get(flipper.getDisplayedChild()).getId());
            }
        }

        @Override
        public void onShowNext(NotifiableViewFlipper flipper) {
            if (flipper.getDisplayedChild() < radioButtons.size()) {
                radioGroup.check(radioButtons.get(flipper.getDisplayedChild()).getId());
            }
        }
    };
}
