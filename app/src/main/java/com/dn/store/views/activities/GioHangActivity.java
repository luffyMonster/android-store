package com.dn.store.views.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dn.store.R;
import com.dn.store.models.Cart;
import com.dn.store.views.adapters.GioHangAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.List;

//import android.widget.Toolbar;

public class GioHangActivity extends AppCompatActivity {
    static String TAG = GioHangActivity.class.getName();
    ListView lvgiohang;
    TextView txtthongbao;
    TextView txttongtien;
    Button btnthanhtoan, btntieptucmua;
    Toolbar toolbargiohang;
    GioHangAdapter giohangAdapter;
    private DatabaseReference mCartRef;
    private ValueEventListener mCartListener;
    List<Cart> carts;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        mCartRef = FirebaseDatabase.getInstance().getReference()
                .child("carts");

        Anhxa();
        //click back to toolbar
        ActionToolbar();
        //thong bao neu k co du lieu
        CheckData();
        EvenUltil();

        //bat su kien cho cac button
        EventButton();

    }

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener cartListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get List Data from Firebase
                //Do du lieu ra view
                //Sua view cho dep
                //Check authenticate

                // Get Post object and use the values to update the UI
//                Cart product = dataSnapshot.getValue(Product.class);
//                // [START_EXCLUDE]
//                Picasso.get().load(product.getImage()).into(imgChitiet);
//                txtten.setText(product.getName());
//                txtgia.setText(String.valueOf(product.getPrice()));
//
//                String details = "";
//                for (String detail:product.getDetails()){
//                    detail += detail;
//                    detail += "\n";
//                }
//                txtmota.setText(details);
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadCart:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(GioHangActivity.this, "Failed to load cart.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mCartRef.addValueEventListener(cartListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mCartListener = cartListener;

    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mCartListener != null) {
            mCartRef.removeEventListener(mCartListener);
        }
    }

    private void EventButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (carts.size() > 0){
                    Intent intent = new Intent();
                } else {
//                    CheckConnection.ShowToast_Short(getApplicationContext(), "Gio hang cua ban chua co san pham de thanh toan");
                }
            }
        });
    }

    private void EvenUltil() {
        long tongtien = 0;
        for (int i = 0; i < carts.size(); i++){
            tongtien += carts.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###, ###, ###");
        txttongtien.setText(decimalFormat.format(tongtien) + " Đ");
    }

    private void CheckData() {
        if(carts.size() <= 0){
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility((View.INVISIBLE));
        }else{
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility((View.VISIBLE));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ActionToolbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void Anhxa() {
        lvgiohang = (ListView) findViewById(R.id.listviewgiohang);
        txtthongbao = (TextView) findViewById(R.id.textviewthongbao);
        txttongtien = (TextView) findViewById(R.id.textviewtongtien);
        btnthanhtoan = (Button) findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmua = (Button) findViewById(R.id.buttontieptucmuahang);
        toolbargiohang = (Toolbar) findViewById(R.id.toolbargiohang);
//        giohangAdapter = new Giohangadapter(Giohang.this, MainActivity.manggiohang);
        lvgiohang.setAdapter(giohangAdapter);
    }
}
