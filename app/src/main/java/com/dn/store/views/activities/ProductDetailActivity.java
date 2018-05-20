package com.dn.store.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.dn.store.R;
import com.dn.store.models.Product;
import com.dn.store.models.Sanpham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ProductDetailActivity extends AppCompatActivity {

    static String TAG = ProductDetailActivity.class.getName();
    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtten, txtgia, txtmota;
    RatingBar ratingBar;
    TextView ratingbarCount;
    Spinner spinner;
    Button btndatmua;

    String mProdKey;
    DatabaseReference mProductRef;
    private ValueEventListener mProductListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        // Get post key from intent
        mProdKey = getIntent().getStringExtra(EXTRA_PRODUCT_KEY);
        if (mProdKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_PRODUCT_KEY");
        }
        mProductRef = FirebaseDatabase.getInstance().getReference()
                .child("products").child(mProdKey);

        Anhxa();
        ActionToolbar();
        GetInformation();
        CatchEvenSpinner();
    }

    private void CatchEvenSpinner() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);

    }

    private void GetInformation() {
        int id = 0;
        String TenChitiet = "";
        int GiaChitiet = 0;
        String HinhanhChitiet = "";
        String MotaChitiet = "";
        int Idsanpham = 0;
//        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
//        id = sanpham.getID();
//        TenChitiet = sanpham.getTensanpham();
//        GiaChitiet = sanpham.getGiasanpham();
//        HinhanhChitiet = sanpham.getHinhanhsanpham();
//        MotaChitiet = sanpham.getMotasanpham();
//        Idsanpham = sanpham.getIDSanpham();
//        txtten.setText(TenChitiet);
//        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        txtgia.setText("Gia: " + decimalFormat.format(GiaChitiet) + "D");
//        txtmota.setText(MotaChitiet);
//        Picasso.get().load(HinhanhChitiet).into(imgChitiet);

    }

    @Override
    public void onStart() {
        super.onStart();

        ValueEventListener product = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                Picasso.get().load(product.getImage()).into(imgChitiet);
                txtten.setText(product.getName());
                txtgia.setText(String.valueOf(product.getPrice()));


                String details = "";
                for (String detail:product.getDetails()){
                    detail += detail;
                    detail += "\n";
                }
                txtmota.setText(details);
                ratingBar.setRating(product.getRating());
                ratingbarCount.setText("(" + product.getRatingCount() +")");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadProduct:onCancelled", databaseError.toException());
                Toast.makeText(ProductDetailActivity.this, "Failed to load product.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mProductRef.addValueEventListener(product);

        mProductListener = product;

    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
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
        btndatmua = findViewById(R.id.buttondatmua);
    }

    public static String EXTRA_PRODUCT_KEY =  "PRODUCT_KEY";
}
