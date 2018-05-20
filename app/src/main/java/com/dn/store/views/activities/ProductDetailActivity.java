package com.dn.store.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.dn.store.R;
import com.dn.store.models.Cart;
import com.dn.store.models.Product;
import com.dn.store.utils.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    static String TAG = ProductDetailActivity.class.getName();
    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtten, txtgia, txtmota;
    RatingBar ratingBar;
    TextView ratingbarCount;
    Spinner spinner;
    Button btndatmua;
    int id = 0;
    String TenChitiet = "";
    int GiaChitiet = 0;
    String HinhanhChitiet = "";

    Product product;
    int soLuongSp = 1;
    String mProdKey;
    DatabaseReference mProductRef;
    private ValueEventListener mProductListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        mProdKey = getIntent().getStringExtra(EXTRA_PRODUCT_KEY);
        if (mProdKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_PRODUCT_KEY");
        }
        mProductRef = FirebaseDatabase.getInstance().getReference()
                .child("products").child(mProdKey);

        Anhxa();
        ActionToolbar();
        CatchEvenSpinner();
        EventButton();
    }

    private void EventButton() {
        final Context context = this;
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                if (mAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Cart cart = new Cart();
                    cart.setIdsp(mProdKey);
                    cart.setGiasp(product.getPrice());
                    cart.setHinhsp(product.getImage());
                    cart.setSoluongsp(soLuongSp);
                    cart.setTensp(product.getName());


                    Utils.writeNewCart(mAuth.getUid(), cart, new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Log.d("Success", String.valueOf(o));
                            Utils.showToastShort(context, "Thêm vào giỏ hàng thành công");
                            Intent intent = new Intent(ProductDetailActivity.this, GioHangActivity.class);
                            startActivity(intent);
                        }
                    }, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Utils.showToastShort(context, "Thêm vào giỏ hàng thất bại");
                            Log.d("Failure", String.valueOf(e.getMessage()));
                        }
                    });

                }


            }
        });
    }


    private void CatchEvenSpinner() {
        final Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                soLuongSp = soluong[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                soLuongSp = 1;
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                product = dataSnapshot.getValue(Product.class);
                Picasso.get().load(product.getImage()).into(imgChitiet);
                txtten.setText(product.getName());
                txtgia.setText(String.valueOf(product.getPrice()));


                String details = "";
                for (String detail : product.getDetails()) {
                    details += detail;
                    details += "\n";
                }
                txtmota.setText(details);
                ratingBar.setRating(product.getRating());
                ratingbarCount.setText("(" + product.getRatingCount() + ")");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadProduct:onCancelled", databaseError.toException());
                Toast.makeText(ProductDetailActivity.this, "Failed to load product.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mProductRef.addValueEventListener(postListener);

        mProductListener = postListener;

    }

    @Override
    public void onStop() {
        super.onStop();

        if (mProductListener != null) {
            mProductRef.removeEventListener(mProductListener);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarChitiet = findViewById(R.id.toolbarchitietsanpham);
        imgChitiet = findViewById(R.id.imageviewchitietsanpham);
        txtten = findViewById(R.id.textviewtenchitietsanpham);
        txtgia = findViewById(R.id.textviewgiachitietsanpham);
        txtmota = findViewById(R.id.textviewmotachitietsanpham);
        ratingBar = findViewById(R.id.product_rating_bar);
        ratingbarCount = findViewById(R.id.product_rating_count);
        spinner = findViewById(R.id.spinner);
        btndatmua = findViewById(R.id.btnAddToCart);
    }

    public static String EXTRA_PRODUCT_KEY = "PRODUCT_KEY";
}
