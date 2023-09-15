package com.prakruthi.ui.ui.home.category;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.GetProductsList;
import com.prakruthi.ui.ui.home.HomeFragment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeCategoryRecyclerAdaptor extends RecyclerView.Adapter<HomeCategoryRecyclerAdaptor.ViewHolder> {

    private List<HomeCategoryModal> homeCategoryModalList;
    GetProductsList.OnCategoryProductsFetchedListner listner;

    public HomeCategoryRecyclerAdaptor(List<HomeCategoryModal> homeCategoryModalList , GetProductsList.OnCategoryProductsFetchedListner listner) {
        this.homeCategoryModalList = homeCategoryModalList;
        this.listner = listner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_category_recycle_view, parent, false);
        return new ViewHolder(view);
    }

    private int selectedItemPosition = -1; // Initially no item is selected
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeCategoryModal homeCategoryModal = homeCategoryModalList.get(position);
        holder.productTextView.setText(homeCategoryModal.getName());
        Glide.with(holder.itemView.getContext())
                .load(homeCategoryModal.getAttachment())
                .placeholder(R.drawable.baseline_circle_24) // Optional placeholder image to show while loading the actual image.
                .into(holder.productImageView);

        if (position % 2 == 0) {
            holder.productImageView.setElevation(8f); // Set elevation for even positions.
        } else {
            holder.productImageView.setElevation(0f); // Remove elevation for odd positions.
        }

        // Check if the current position is the selected item
        boolean isSelected = position == selectedItemPosition;

        // Set the background drawable based on the selection
        Drawable backgroundDrawable = isSelected ? createRoundedRectangleDrawable(holder.itemView.getContext(), R.color.Primary,80) : null;
        holder.productImageView.setBackground(backgroundDrawable);

        holder.itemView.setOnClickListener(v -> {
            // Update the selected item position
            selectedItemPosition = holder.getAdapterPosition();

            // Notify the adapter that the data has changed to refresh the views
            notifyDataSetChanged();

            // Rest of your code...
            HomeFragment.HomeShimmerProductRecyclerView.showShimmerAdapter();
            String categoryid = String.valueOf(homeCategoryModalList.get(position).getId());
            GetProductsList getProductsList = new GetProductsList(listner, categoryid);
            getProductsList.HitGetProductListAPi();
        });
    }



    // Helper method to create a rounded rectangle drawable
    private Drawable createRoundedRectangleDrawable(Context context, @ColorRes int colorRes, float cornerRadius) {
        int color = ContextCompat.getColor(context, colorRes);
        float[] radii = {cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius};
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(radii, null, null));
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }
    @Override
    public int getItemCount() {
        return homeCategoryModalList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView productImageView;
        public TextView productTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.Products_Image_View);
            productTextView = itemView.findViewById(R.id.Products_Text_View);
            productTextView.setSelected(true);
        }
    }
}