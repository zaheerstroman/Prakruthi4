package com.prakruthi.ui.ui.search;

import static com.airbnb.lottie.L.TAG;

import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.productPage.ProductPage;
import com.prakruthi.ui.ui.productPage.ProductPagerAdaptor;

import java.util.List;

public class SearchAdaptor extends RecyclerView.Adapter<SearchAdaptor.ViewHolder> {

    List<SearchModle> searchModles;

    public SearchAdaptor(List<SearchModle> searchModles)
    {
        this.searchModles = searchModles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recycler_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(searchModles.get(position).getAttachments().get(0)).into(holder.Search_product_image);
        holder.Search_Product_name.setText(searchModles.get(position).getName());
        holder.Search_Product_Price.setText(searchModles.get(position).getCustomerPrice());
        holder.Search_Product_Price_real.setText(searchModles.get(position).getActualPrice());
        if (Variables.departmentId.equals(2))
        {
            holder.Search_Product_Price.setText(searchModles.get(position).getCustomerPrice());
        } else if (Variables.departmentId.equals(3)) {
            holder.Search_Product_Price.setText(searchModles.get(position).getDealerPrice());
        } else if (Variables.departmentId.equals(4)) {
            holder.Search_Product_Price.setText(searchModles.get(position).getMartPrice());
        }
        holder.Search_Product_Description.setText(searchModles.get(position).getDescription());
        int ratingStars = Integer.parseInt(searchModles.get(position).getRating());
        Resources res = holder.itemView.getContext().getResources();
        float starSize = 25; // Replace with your custom sdp dimension resource
        int marginStart = 3; // Replace with your desired margin start value in dp
        for (int i = 0; i < ratingStars; i++) {
            ImageView imageView = new ImageView(holder.itemView.getContext());
            imageView.setImageResource(R.drawable.star_vector); // Replace with your actual rating image resource
            imageView.setVisibility(View.VISIBLE);

            int sizeInPixels = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    starSize,
                    res.getDisplayMetrics()
            );

            LinearLayout.LayoutParams starParams = new LinearLayout.LayoutParams(sizeInPixels, sizeInPixels);
            starParams.setMarginStart((int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    marginStart,
                    res.getDisplayMetrics()
            ));

            holder.RatingLayout.addView(imageView, starParams);
        }

        TextView ratingCountTextView = new TextView(holder.itemView.getContext());
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textParams.setMarginStart((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2, // Replace with your desired margin start for the TextView in dp
                res.getDisplayMetrics()
        ));
        ratingCountTextView.setLayoutParams(textParams);
        ratingCountTextView.setTextSize(15); // Replace with your custom sdp dimension resource
        ratingCountTextView.setText(searchModles.get(position).getCount_rating()); // Replace with your desired text
        holder.RatingLayout.addView(ratingCountTextView);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ProductPage.class);
            intent.putExtra("productId",String.valueOf(searchModles.get(position).getId()));
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return searchModles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView Search_product_image;
        TextView Search_Product_name , Search_Product_Price , Search_Product_Price_real , Search_Product_Description ;
        LinearLayout RatingLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Search_product_image = itemView.findViewById(R.id.Search_product_image);
            Search_Product_name = itemView.findViewById(R.id.Search_Product_name);
            Search_Product_Price = itemView.findViewById(R.id.Search_Product_Price);
            Search_Product_Price_real = itemView.findViewById(R.id.Search_Product_Price_real);
            Search_Product_Description = itemView.findViewById(R.id.Search_Product_Description);
            RatingLayout = itemView.findViewById(R.id.RatingLayout);
        }
    }
}
