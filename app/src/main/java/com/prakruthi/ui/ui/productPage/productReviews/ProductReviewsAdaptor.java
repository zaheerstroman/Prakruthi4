package com.prakruthi.ui.ui.productPage.productReviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.willy.ratingbar.RotationRatingBar;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductReviewsAdaptor extends RecyclerView.Adapter<ProductReviewsAdaptor.ReviewViewHolder> {

    public ArrayList<ProductReviewsModel> productReviewsModels;

    public ProductReviewsAdaptor(ArrayList<ProductReviewsModel> productReviewsModels)
    {
        this.productReviewsModels = productReviewsModels;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_reviews_bottom_popup_recycler, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        ProductReviewsModel productReviewsModel = productReviewsModels.get(position);

        holder.ReviewRatingBar.setRating(Float.parseFloat(productReviewsModel.rating));

//        holder.ReviewRatingBar.setRating(Float.parseFloat(productReviewsModel.rating));

        holder.ReviewText.setText(productReviewsModel.review);
    }

    @Override
    public int getItemCount() {
        return productReviewsModels.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ReviewerPrifile;
        RotationRatingBar ReviewRatingBar;
        TextView ReviewText;
        ReviewViewHolder(View itemView) {
            super(itemView);
            ReviewerPrifile = itemView.findViewById(R.id.circleImageView);
            ReviewRatingBar = itemView.findViewById(R.id.ReviewRatingBar);
            ReviewText = itemView.findViewById(R.id.textView);
        }
    }
}
