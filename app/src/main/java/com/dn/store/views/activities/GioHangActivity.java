package com.dn.store.views.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dn.store.R;
import com.dn.store.models.Cart;
import com.dn.store.models.CartListener;
import com.dn.store.models.DataListener;
import com.dn.store.models.Product;
import com.dn.store.utils.Utils;
import com.dn.store.views.adapters.GioHangAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//import android.widget.Toolbar;

public class GioHangActivity extends AppCompatActivity  {
    static String TAG = GioHangActivity.class.getName();

    RecyclerView recycleViewGioHang;
    TextView txtthongbao;
    TextView txttongtien;
    Button btnthanhtoan;
    Toolbar toolbargiohang;
    private FirebaseAuth mAuth;


    GioHangAdapter giohangAdapter;
    private DatabaseReference mDatabase;
    private ValueEventListener mCartListener;
    private List<Cart> carts;
    private LinearLayoutManager mManager;
    private long tongtien;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        carts = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        Anhxa();
        ActionToolbar();
        EventButton();

    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void EventButton() {
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (carts.size() > 0) {
                    if (mAuth.getCurrentUser() == null) {
                        Intent intent = new Intent(GioHangActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(GioHangActivity.this, Thongtinkhachhang.class);
                        startActivity(intent);
                    }
                } else {
                    Utils.showToastShort(getApplicationContext(), "Gio hang cua ban chua co san pham de thanh toan");
                }
            }
        });
    }

    public void EvenUltil() {
        tongtien = 0;
        for (int i = 0; i < carts.size(); i++) {
            tongtien += carts.get(i).getGiasp() * carts.get(i).getSoluongsp();
        }
        txttongtien.setText(String.valueOf(tongtien));
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
        recycleViewGioHang = findViewById(R.id.recycleViewGioHang);
        txtthongbao = (TextView) findViewById(R.id.textviewthongbao);
        txttongtien = (TextView) findViewById(R.id.textviewtongtien);
        btnthanhtoan = (Button) findViewById(R.id.buttonthanhtoangiohang);
        toolbargiohang = (Toolbar) findViewById(R.id.toolbargiohang);

        mManager = new LinearLayoutManager(this);
        recycleViewGioHang.setHasFixedSize(false);
        recycleViewGioHang.setNestedScrollingEnabled(false);
        recycleViewGioHang.setLayoutManager(mManager);
        String uid = mAuth.getUid();
        Query postsQuery = mDatabase.child("carts").child(uid).orderByChild("tensp");
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(postsQuery, Cart.class)
                .build();

        DataListener dataListener = new DataListener() {
            @Override
            public void onChanged() {
                carts = new ArrayList<>();
                if (giohangAdapter.getItemCount() == 0) {
                    txtthongbao.setVisibility(View.VISIBLE);
                } else {
                    txtthongbao.setVisibility(View.GONE);
                }

                for (int i = 0; i < giohangAdapter.getItemCount(); i++) {
                    carts.add(i, giohangAdapter.getItem(i));
                }

                EvenUltil();
            }

            @Override
            public void onError() {

            }
        };

        CartListener cartListener = new CartListener() {
            @Override
            public void onPlus(long price) {
                tongtien += price;
                txttongtien.setText(String.valueOf(tongtien));
            }

            @Override
            public void onMinus(long price) {
                tongtien -= price;
                txttongtien.setText(String.valueOf(tongtien));
            }
        };
        giohangAdapter = new GioHangAdapter(options, this, dataListener, cartListener);
        recycleViewGioHang.setAdapter(giohangAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (giohangAdapter != null) {
            giohangAdapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (giohangAdapter != null) {
            giohangAdapter.startListening();
        }
    }


}
