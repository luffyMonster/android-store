package com.dn.store.views.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dn.store.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.dn.store.R.color.colorWhite;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout lnlDangNhap, lnlDangKy;
    Button btnDangNhap, btnDangKy, btnDongYDN, btnDongYDK;
    EditText etemailDangNhap, etpasswordDangNhap,etemailDangKy, etpasswordDangKy;

    Toolbar toolbarlogin;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        Anhxa();
        ActionToolbar();

        btnDangNhap.setOnClickListener(this);
        btnDangKy.setOnClickListener(this);
        btnDongYDK.setOnClickListener(this);
        btnDongYDN.setOnClickListener(this);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarlogin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void DangKy() {
        String email = etemailDangKy.getText().toString().trim();
        String password = etpasswordDangKy.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Đăng ký thành công",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Email hoặc mật khẩu không hợp lệ",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void DangNhap() {
        String email = etemailDangNhap.getText().toString().trim();
        final String password = etpasswordDangNhap.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập password!", Toast.LENGTH_SHORT).show();
            return;
        }

        //authenticate user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                Toast.makeText(LoginActivity.this, "Mật khẩu phải >= 6 kí tự", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnDangNhap:
                lnlDangNhap.setVisibility(View.VISIBLE);
                lnlDangKy.setVisibility(View.GONE);
                btnDangNhap.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnDangKy.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.btnDangKy:
                lnlDangNhap.setVisibility(View.GONE);
                lnlDangKy.setVisibility(View.VISIBLE);
                btnDangKy.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnDangNhap.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.btnDongYDK:
                DangKy();
                break;
            case R.id.btnDongYDN:
                DangNhap();
                break;
        }
    }

    public void Anhxa() {
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnDongYDN = findViewById(R.id.btnDongYDN);
        btnDongYDK = findViewById(R.id.btnDongYDK);
        etemailDangNhap = findViewById(R.id.etemailDangNhap);
        etemailDangKy = findViewById(R.id.etemailDangKy);
        etpasswordDangNhap = findViewById(R.id.etpasswordDangNhap);
        etpasswordDangKy = findViewById(R.id.etpasswordDangKy);
        lnlDangNhap = findViewById(R.id.lnlDangNhap);
        lnlDangKy = findViewById(R.id.lnlDangKy);
        toolbarlogin = (Toolbar) findViewById(R.id.toolbardangnhap);

    }


}
