package com.dn.store.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dn.store.R;
import com.dn.store.models.Category;
import com.dn.store.models.DataListener;
import com.dn.store.views.activities.CategoryDetailActivity;
import com.dn.store.views.activities.ProductDetailActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;

public class CategoriesAdapter extends FirebaseRecyclerAdapter<Category, CategoriesAdapter.CategoryItemHolder> {
    private Context context;
    private DataListener listener;
    private int rootId;

    public CategoriesAdapter(@NonNull FirebaseRecyclerOptions<Category> options, Context context, DataListener listener, int rootId) {
        super(options);
        this.context = context;
        this.listener = listener;
        this.rootId = rootId;
    }

    @NonNull
    @Override
    public CategoryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryItemHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull CategoryItemHolder holder, int position, @NonNull Category model) {
        Picasso.get().load(model.getImage()).placeholder(R.drawable.img_holder)
                .fit().into(holder.img);
        holder.name.setText(model.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryDetailActivity.class);
                intent.putExtra(CategoryDetailActivity.ROOT_ID, rootId);
                context.startActivity(intent);
            }
        });
    }

    public static class CategoryItemHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name;

        public CategoryItemHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.category_image);
            name = itemView.findViewById(R.id.category_name);
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
