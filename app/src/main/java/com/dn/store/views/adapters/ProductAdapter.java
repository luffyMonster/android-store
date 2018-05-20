package com.dn.store.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dn.store.R;
import com.dn.store.models.DataListener;
import com.dn.store.models.Product;
import com.dn.store.views.activities.ProductDetailActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends FirebaseRecyclerAdapter<Product, ProductAdapter.ProductItemViewHolder>  {

    private Context context;
    int resId;
    private DataListener listener;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProductAdapter(@NonNull FirebaseRecyclerOptions<Product> options, Context context, DataListener listener, int resId) {
        super(options);
        this.context = context;
        this.listener = listener;
        if (resId != R.layout.product_item && resId != R.layout.product_item_linear) {
            this.resId = R.layout.product_item;
        } else {
            this.resId = resId;
        }
    }


    @NonNull
    @Override
    public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        view.setMinimumHeight(parent.getMeasuredHeight() / 4);
        return new ProductItemViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull final ProductItemViewHolder holder, int position, @NonNull Product p) {

        final DatabaseReference prodRef = getRef(position);

        final String prodKey = prodRef.getKey();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_KEY, prodKey);
                context.startActivity(intent);
            }
        });

        Picasso.get().load(p.getImage()).into(holder.mProductImage);
        holder.mProductPrice.setText(String.valueOf(p.getPrice()));
        holder.mName.setText(p.getName());
        holder.mProductRatingBar.setRating(p.getRating());
        holder.mProductRatingCount.setText("(" + p.getRatingCount() +")");
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



    public  class ProductItemViewHolder extends RecyclerView.ViewHolder{

        public ImageView mProductImage;
        public TextView mProductPrice;
        public TextView mName;
        public RatingBar mProductRatingBar;
        public TextView mProductRatingCount;


        public ProductItemViewHolder(View itemView) {
            super(itemView);
            mProductImage = itemView.findViewById(R.id.product_image);
            mName = itemView.findViewById(R.id.product_description);
            mProductPrice = itemView.findViewById(R.id.product_price);
            mProductRatingBar = itemView.findViewById(R.id.product_rating_bar);
            mProductRatingCount = itemView.findViewById(R.id.product_rating_count);
        }

    }

}
