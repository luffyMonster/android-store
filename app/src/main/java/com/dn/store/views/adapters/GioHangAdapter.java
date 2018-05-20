package com.dn.store.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dn.store.R;
import com.dn.store.models.Cart;
import com.dn.store.models.DataListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;


public class GioHangAdapter extends FirebaseRecyclerAdapter<Cart, GioHangAdapter.ViewHolder> {
    private Context context;
    private DataListener listener;


    public GioHangAdapter(@NonNull FirebaseRecyclerOptions<Cart> options, Context context, DataListener listener) {
        super(options);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position, @NonNull final Cart cart) {
        Picasso.get().load(cart.getHinhsp()).into(viewHolder.imggiohang);
        viewHolder.txttengiohang.setText(cart.getTensp());
        viewHolder.txtgiagiohang.setText(String.valueOf(cart.getGiasp()) + " Đ");
        viewHolder.btnvalues.setText(String.valueOf(cart.getSoluongsp()));

        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi = Integer.parseInt(viewHolder.btnvalues.getText().toString()) + 1;
                int slht = cart.getSoluongsp();
                long giaht = cart.getGiasp();
                cart.setSoluongsp(slmoi);
                long giamoi = 0;
                if (slht > 0) {
                    giamoi = (giaht*slmoi) /slht;
                } else {
                    slht = 0;
                }
                cart.setGiasp(giamoi);
                viewHolder.txtgiagiohang.setText(String.valueOf(giamoi) + " Đ");
                viewHolder.btnvalues.setText(String.valueOf(slmoi));
            }
        });

        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi = Integer.parseInt(viewHolder.btnvalues.getText().toString()) - 1;
                int slht = cart.getSoluongsp();
                long giaht = cart.getGiasp();
                cart.setSoluongsp(slmoi);
                long giamoi = 0;
                if (slht > 0) {
                    giamoi = (giaht*slmoi) /slht;
                } else {
                    slht = 0;
                }
                cart.setGiasp(giamoi);
                viewHolder.txtgiagiohang.setText(String.valueOf(giamoi) + " Đ");
                viewHolder.btnvalues.setText(String.valueOf(slmoi));
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_giohang, parent, false);
        return new GioHangAdapter.ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txttengiohang, txtgiagiohang;
        public ImageView imggiohang;
        public Button btnminus, btnvalues, btnplus;

        public ViewHolder(View itemView) {
            super(itemView);
            txttengiohang =  itemView.findViewById(R.id.textviewtengiohang);
            txtgiagiohang = (TextView) itemView.findViewById(R.id.textviewgiagiohang);
            imggiohang = (ImageView) itemView.findViewById(R.id.imageviewgiohang);
            btnminus = (Button) itemView.findViewById(R.id.buttonminus);
            btnvalues = (Button) itemView.findViewById(R.id.buttovalues);
            btnplus = (Button) itemView.findViewById(R.id.buttonplus);
        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        if (listener != null) {
            listener.onChanged();
        }
        notifyDataSetChanged();
    }

    @Override
    public void onError(@NonNull DatabaseError error) {
        super.onError(error);
        if (listener != null) {
            listener.onError();
        }
    }


}
