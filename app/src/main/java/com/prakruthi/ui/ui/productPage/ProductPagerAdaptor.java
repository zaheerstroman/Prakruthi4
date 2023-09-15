package com.prakruthi.ui.ui.productPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;

import java.util.List;

public class ProductPagerAdaptor extends RecyclerView.Adapter<ProductPagerAdaptor.ProductViewHolder> {

    Context context;
    List<String> list;
    public ProductPagerAdaptor(Context context , List<String> list)
    {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_page_viewpager_imageview, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Load image using Glide library
        Glide.with(context)
                .load(list.get(position))
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView productImage;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image_view);
        }
    }
}
