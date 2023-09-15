package com.prakruthi.ui.ui.profile.recentProducts;

import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.productPage.ProductPage;
import com.willy.ratingbar.RotationRatingBar;

import java.util.ArrayList;

public class RecentProductAdaptor extends RecyclerView.Adapter<RecentProductAdaptor.ViewHolder> {

    ArrayList<RecentProductModel> recentProductModels;

    public RecentProductAdaptor(ArrayList<RecentProductModel> recentProductModels)
    {
        this.recentProductModels = recentProductModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create your view and inflate it from the layout XML
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_product_recycle_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentProductModel recentProductModel = recentProductModels.get(position);
        holder.productTextView.setText(recentProductModel.getName());
        Glide.with(holder.itemView.getContext())
                .load(recentProductModel.getAttachment())
                .placeholder(R.drawable.baseline_circle_24) // Optional placeholder image to show while loading the actual image.
                .into(holder.productImageView);
        holder.itemView.setOnClickListener(v->{
            holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(),ProductPage.class)
                    .putExtra("productId",String.valueOf(recentProductModel.getProduct_id())));
        });
        holder.HomeRatingBar.setRating(Float.parseFloat(recentProductModel.getRating()));
        holder.HomeRatingCount.setText(recentProductModel.getCount_rating());
        if (Variables.departmentId.equals(2))
        {
            holder.Home_Product_Price.setText(recentProductModel.getCustomer_price());
        } else if (Variables.departmentId.equals(3)) {
            holder.Home_Product_Price.setText(recentProductModel.getDelar_price());
        } else if (Variables.departmentId.equals(4)) {
            holder.Home_Product_Price.setText(recentProductModel.getMart_price());
        }
        holder.Home_Product_Price_real.setText(recentProductModel.getActual_price());
        holder.Home_Product_Price_real.setPaintFlags(holder.Home_Product_Price_real.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return recentProductModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView productImageView;
        public TextView productTextView , HomeRatingCount , Home_Product_Price , Home_Product_Price_real;
        public RotationRatingBar HomeRatingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.homepage_product_recycle_view_imageview);
            productTextView = itemView.findViewById(R.id.homepage_product_recycle_view_name_text);
            HomeRatingBar = itemView.findViewById(R.id.HomeRatingBar);
            HomeRatingCount = itemView.findViewById(R.id.HomeRatingCount);
            Home_Product_Price = itemView.findViewById(R.id.Home_Product_Price);
            Home_Product_Price_real = itemView.findViewById(R.id.Home_Product_Price_real);
        }
    }
}
