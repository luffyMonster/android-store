package com.dn.store.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.dn.store.R;
import com.dn.store.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Thongtinkhachhang extends AppCompatActivity {
    EditText edittenKH, editsdt, editemail, editdiachi;
    Button btnxacnhan, btntrove;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
//        Anhxa();
        btntrove.getRootView().setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void EvenButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edittenKH.getText().toString().trim();
                final String sdt = editsdt.getText().toString().trim();
                final String email = editemail.getText().toString().trim();
                final String diachi = editdiachi.getText().toString().trim();
                if (validate(ten, sdt, email, diachi)) {


                } else {
                    Utils.showToastShort(getApplicationContext(), "Bạn hãy kiểm tra laij dữ liệu!");
                }

            }
//            public void onClick(View view) {
//                String ten = edittenKH.getText().toString().trim();
//                final String sdt = editsdt.getText().toString().trim();
//                final String email = editemail.getText().toString().trim();
//                final String diachi = editdiachi.getText().toString().trim();
//                if (ten.length() > 0 && sdt.length() > 0 && email.length() > 0 && diachi.length() > 0) {
//                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(final String madonhang) {
//                            Log.d("madonhang", madonhang);
//                            if (Integer.parseInt(madonhang) > 0) {
//                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//                                StringRequest request = new StringRequest(Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//                                        if (response.equals("1")) {
//                                            MainActivity.manggiohang.clear();
//                                            Utils.showToastShort(getApplicationContext(), "Bạn đã thêm dữ liệu giỏ hàng thành công!");
//                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                            startActivity(intent);
//                                            Utils.showToastShort(getApplicationContext(), "Mời bạn tiếp tục mua hàng!");
//                                        } else {
//                                            Utils.showToastShort(getApplicationContext(), "Dữ liệu giỏ hàng của bạn bị lỗi!");
//                                        }
//                                    }
//                                } new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//
//                                    }
//                                }){
//                                    protected Map<String, String> getParams () throws
//                                    AuthFailureError {
//                                        JSONArray jsonArray = new JSONArray();
//                                        for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
//                                            JSONObject jsonObject = new JSONObject();
//                                            try {
//                                                jsonObject.put("madonhang", madonhang);
//                                                jsonObject.put("masanpham", MainActivity.manggiohang.get(i).getIdsp());
//                                                jsonObject.put("tensanpham", MainActivity.manggiohang.get(i).getTensp());
//                                                jsonObject.put("giasanpham", MainActivity.manggiohang.get(i).getGiasp());
//                                                jsonObject.put("soluongsanpham", MainActivity.manggiohang.get(i).getSoluongsp());
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                            }
//                                            jsonArray.put(jsonObject);
//                                        }
//                                        HashMap<String, String> hashMap = new HashMap<String, String>();
//                                        hashMap.put("json", jsonArray.toString());
//                                        return hashMap;
//                                    }
//                                } ;
//                                queue.add(request);
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                        }
//                    }) {
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            HashMap<String, String> hashMap = new HashMap<String, String>();
//                            hashMap.put("tenkhachhang", ten);
//                            hashMap.put("sodienthoai", sdt);
//                            hashMap.put("email", email);
//                            hashMap.put("diachi", diachi);
//                            return hashMap;
//                        }
//                    };
//                    RequestQueue.add(stringRequest);
//                } else {
//                    CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra laij dữ liệu!");
//                }
//            }
        });

    }

    private boolean validate(String ten, String sdt, String email, String diachi) {
        return ten.length() > 0 && sdt.length() > 0 && email.length() > 0 && diachi.length() > 0;
    }

    private void Anhxa() {
        edittenKH = (EditText) findViewById(R.id.edittexttenkhachhang);
        editsdt = (EditText) findViewById(R.id.edittextsodienthoai);
        editemail = (EditText) findViewById(R.id.edittextemail);
        editdiachi = (EditText) findViewById(R.id.editdiachiKH);
        btnxacnhan = (Button) findViewById(R.id.buttonxacnhan);
        btntrove = (Button) findViewById(R.id.buttontrove);
    }
}
