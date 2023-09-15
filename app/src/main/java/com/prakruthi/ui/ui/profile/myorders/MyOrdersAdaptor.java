package com.prakruthi.ui.ui.profile.myorders;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.prakruthi.ui.ui.productPage.ProductPage;
import com.prakruthi.ui.ui.search.SearchAdaptor;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyOrdersAdaptor extends RecyclerView.Adapter<MyOrdersAdaptor.ViewHolder> {

    List<MyOrdersModal> myOrdersModals;


    public MyOrdersAdaptor(List<MyOrdersModal> myOrdersModals){
        this.myOrdersModals = myOrdersModals;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorderslistlayout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MyOrdersModal myOrdersModal = myOrdersModals.get(position);

        holder.MyOrdersProductName.setText(myOrdersModal.getName());
        holder.MyOrdersProductSubInformation.setText(myOrdersModal.getTracking_status());
//        holder.MyOrdersProductSubAmount.setText(myOrdersModal.getAmount());


        holder.MyOrdersProductSubAmount.setText("₹  ");
        holder.MyOrdersProductSubAmount.append(myOrdersModal.getAmount());

        Glide.with(holder.itemView.getContext())
                .load(myOrdersModal.getAttachment())
                .placeholder(R.drawable.baseline_circle_24)
                .into(holder.MyOrdersProductImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                holder.MyOrdersProductImage.getContext().startActivity(new Intent(holder.itemView.getContext(), ProductPage.class)
                        .putExtra("productId",String.valueOf(myOrdersModal.getProduct_id())));

            }
        });


    }

    @Override
    public int getItemCount() {
        return myOrdersModals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView MyOrdersProductImage;
        TextView MyOrdersProductName,MyOrdersProductSubInformation, MyOrdersProductSubAmount;
        ImageButton MyOrdersProductNextBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            MyOrdersProductImage = itemView.findViewById(R.id.MyOrdersProductImage);
            MyOrdersProductName = itemView.findViewById(R.id.MyOrdersProductName);
            MyOrdersProductSubInformation = itemView.findViewById(R.id.MyOrdersProductSubInformation);
            MyOrdersProductSubAmount = itemView.findViewById(R.id.MyOrdersProductSubAmount);

            MyOrdersProductNextBtn = itemView.findViewById(R.id.MyOrdersProductNextBtn);
            MyOrdersProductName.setSelected(true);
        }
    }
}
