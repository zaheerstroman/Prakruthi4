package com.prakruthi.ui.ui.wishlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.AddToCart;
import com.prakruthi.ui.APIs.GetWishlistDetails;
import com.prakruthi.ui.APIs.SaveWishList;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.search.SearchAdaptor;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WishListRecyclerAdaptor extends RecyclerView.Adapter<WishListRecyclerAdaptor.ViewHolder> {

    List<WishListModal> wishListModals;

    AddToCart.OnDataFetchedListner listner;
    SaveWishList.OnSaveWishListDataFetchedListener listner_wishlist;
    public WishListRecyclerAdaptor(List<WishListModal> wishListModals ,  AddToCart.OnDataFetchedListner listner , SaveWishList.OnSaveWishListDataFetchedListener listner_wishlist) {
        this.wishListModals = wishListModals;
        this.listner = listner;
        this.listner_wishlist = listner_wishlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlistlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WishListModal wishListModal = wishListModals.get(position);
        Glide.with(holder.itemView.getContext()).load(wishListModal.getAttachment()).into(holder.wishlist_product_image);
        holder.wishlist_product_name.setText(wishListModal.getName());
        holder.wishlist_product_name.setSelected(true);
//        holder.wishlist_product_price.setText(wishListModal.getCustomerPrice());
        if (Variables.departmentId.equals(2))
        {
//            holder.wishlist_product_price.setText(wishListModal.getCustomerPrice());

            holder.wishlist_product_price.setText("₹  ");
            holder.wishlist_product_price.append(wishListModal.getCustomerPrice());
        }else if (Variables.departmentId.equals(3)) {
//            holder.wishlist_product_price.setText(wishListModal.getDelarPrice());

            holder.wishlist_product_price.setText("₹  ");
            holder.wishlist_product_price.append(wishListModal.getDelarPrice());
        } else if (Variables.departmentId.equals(4)) {
//            holder.wishlist_product_price.setText(wishListModal.getMartPrice());

            holder.wishlist_product_price.setText("₹  ");
            holder.wishlist_product_price.append(wishListModal.getMartPrice());
        }
        holder.wishlist_product_added_date.setText("Added on ");
        holder.wishlist_product_added_date.append(wishListModal.getDate());
        holder.wishlist_product_added_date.setSelected(true);
        holder.wishlist_product_add_to_cart.setOnClickListener(v -> {
            Loading.show(holder.itemView.getContext());
            AddToCart addToCart = new AddToCart(wishListModal.getProduct_id(), "1" , ""  , false , listner);
            addToCart.fetchData();
        });
        holder.wishlist_product_delete.setOnClickListener(v -> {
            Loading.show(holder.itemView.getContext());
            SaveWishList saveWishList = new SaveWishList(listner_wishlist,wishListModal.getProduct_id());
            saveWishList.HitSaveWishListApi("No");
        });

    }


    @Override
    public int getItemCount() {
        return wishListModals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView wishlist_product_image;
        TextView wishlist_product_name, wishlist_product_price, wishlist_product_status, wishlist_product_added_date;
        AppCompatButton wishlist_product_add_to_cart;
        ImageView wishlist_product_delete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wishlist_product_image = itemView.findViewById(R.id.wishlist_product_image);
            wishlist_product_name = itemView.findViewById(R.id.wishlist_product_name);
            wishlist_product_price = itemView.findViewById(R.id.wishlist_product_price);
            wishlist_product_add_to_cart = itemView.findViewById(R.id.wishlist_product_add_to_cart);
            wishlist_product_added_date = itemView.findViewById(R.id.wishlist_product_added_date);
            wishlist_product_delete = itemView.findViewById(R.id.wishlist_product_delete);


            wishlist_product_name.setSelected(true);
            wishlist_product_name.setSelected(true);


        }
    }
}
