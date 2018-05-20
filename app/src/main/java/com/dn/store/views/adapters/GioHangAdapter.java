package com.dn.store.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dn.store.R;
import com.dn.store.models.Cart;
import com.dn.store.models.CartListener;
import com.dn.store.models.DataListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;


public class GioHangAdapter extends FirebaseRecyclerAdapter<Cart, GioHangAdapter.ViewHolder> {
    private Context context;
    private DataListener listener;
    private CartListener cartListener;

    private long currentPrice;
    private int currentQty;

    public GioHangAdapter(@NonNull FirebaseRecyclerOptions<Cart> options, Context context, DataListener listener, CartListener cartListener) {
        super(options);
        this.context = context;
        this.listener = listener;
        this.cartListener = cartListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position, @NonNull final Cart cart) {
        Picasso.get().load(cart.getHinhsp()).into(viewHolder.imggiohang);
        viewHolder.txttengiohang.setText(cart.getTensp());
        viewHolder.txtgiagiohang.setText(String.valueOf(cart.getGiasp()) + " Đ");
        viewHolder.btnvalues.setText(String.valueOf(cart.getSoluongsp()));

        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRef(position).removeValue();
            }
        });

        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long unit = cart.getGiasp();
                long diffQty = 1;
                currentQty += 1;
                if (currentQty > 10){
                    currentQty = 10;
                    diffQty = 0;
                }
                currentPrice = currentQty * unit;

                viewHolder.txtgiagiohang.setText(String.valueOf(currentPrice) + " Đ");
                viewHolder.btnvalues.setText(String.valueOf(currentQty));
                cartListener.onPlus(diffQty*unit);
            }
        });

        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long unit = cart.getGiasp();
                long diffQty = 1;
                currentQty -= 1;
                if (currentQty < 1){
                    currentQty = 1;
                    diffQty = 0;
                }
                currentPrice = currentQty * unit;

                viewHolder.txtgiagiohang.setText(String.valueOf(currentPrice) + " Đ");
                viewHolder.btnvalues.setText(String.valueOf(currentQty));
                cartListener.onMinus(diffQty*unit);
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
        public ImageButton btnRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            txttengiohang =  itemView.findViewById(R.id.textviewtengiohang);
            txtgiagiohang = (TextView) itemView.findViewById(R.id.textviewgiagiohang);
            imggiohang = (ImageView) itemView.findViewById(R.id.imageviewgiohang);
            btnminus = (Button) itemView.findViewById(R.id.buttonminus);
            btnvalues = (Button) itemView.findViewById(R.id.buttovalues);
            btnplus = (Button) itemView.findViewById(R.id.buttonplus);
            btnRemove = itemView.findViewById(R.id.btn_remove);
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
