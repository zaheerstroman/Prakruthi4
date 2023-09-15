package com.prakruthi.ui.ui.home.products;

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

import java.util.List;

public class HomeProductAdaptor extends RecyclerView.Adapter<HomeProductAdaptor.ViewHolder> {

    public static List<HomeProductModel> homeProductModelList;

    public HomeProductAdaptor(List<HomeProductModel> homeProductModelList) {
        this.homeProductModelList = homeProductModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_product_recycle_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeProductModel homeProductModel = homeProductModelList.get(position);
        holder.productTextView.setText(homeProductModel.getName());
        Glide.with(holder.itemView.getContext())
                .load(homeProductModel.getAttachment())
                .placeholder(R.drawable.baseline_circle_24) // Optional placeholder image to show while loading the actual image.
                .into(holder.productImageView);
        holder.itemView.setOnClickListener(v->{
            holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(),ProductPage.class)
                    .putExtra("productId",String.valueOf(homeProductModel.getId())));
        });
        holder.HomeRatingBar.setRating(Float.parseFloat(homeProductModel.getRating()));
//        holder.HomeRatingCount.setText(homeProductModel.getCount_rating());
        holder.HomeRatingCount.setText(homeProductModel.getRating());

        if (Variables.departmentId.equals(2))
        {

            holder.Home_Product_Price.setText("₹  ");
            holder.Home_Product_Price.append(homeProductModel.getCustomerPrice());

//            holder.Home_Product_Price.setText(homeProductModel.getCustomerPrice());
        } else if (Variables.departmentId.equals(3)) {

            holder.Home_Product_Price.setText("₹  ");
            holder.Home_Product_Price.append(homeProductModel.getDealerPrice());

//            holder.Home_Product_Price.setText(homeProductModel.getDealerPrice());
        } else if (Variables.departmentId.equals(4)) {

            holder.Home_Product_Price.setText("₹  ");
            holder.Home_Product_Price.append(homeProductModel.getMartPrice());

//            holder.Home_Product_Price.setText(homeProductModel.getMartPrice());
        }

        holder.Home_Product_Price_real.setText("₹  ");
        holder.Home_Product_Price_real.append(homeProductModel.getActualPrice());

//        holder.Home_Product_Price_real.setText(homeProductModel.getActualPrice());

        holder.Home_Product_Price_real.setPaintFlags(holder.Home_Product_Price_real.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return homeProductModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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
