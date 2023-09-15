package com.prakruthi.ui.ui.cart;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.AddToCart;
import com.prakruthi.ui.APIs.DeleteCartDetails;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.productPage.ProductModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CartRecyclerAdaptor extends RecyclerView.Adapter<CartRecyclerAdaptor.ViewHolder>{

    private List<ProductModel> productModels;
    ArrayList<CartModal> cartCategoryModalList = new ArrayList<>();
    Context context;
    AddToCart.OnDataFetchedListner listner;
    DeleteCartDetails.OnCartItemDeleteAPiHit Deletelistner;

    public CartRecyclerAdaptor(Context context , ArrayList<CartModal> cartCategoryModalList , AddToCart.OnDataFetchedListner listner , DeleteCartDetails.OnCartItemDeleteAPiHit Deletelistner) {
        this.cartCategoryModalList.clear();
        this.cartCategoryModalList = cartCategoryModalList;
        this.context = context;
        this.listner = listner;
        this.Deletelistner = Deletelistner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlistlayout, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {

            CartModal cartData = cartCategoryModalList.get(position);
            holder.CartProductName.setText(cartData.getName());
            holder.CartProductSubInformation.setText(cartData.getDescription());

//            holder.CartProductPrice.setText(cartData.getCustomerPrice());
            if (Variables.departmentId.equals(2))
            {
//                holder.CartProductPrice.setText(cartData.getCustomerPrice());

                holder.CartProductPrice.setText("₹  ");
                holder.CartProductPrice.append(cartData.getCustomerPrice());
            }else if (Variables.departmentId.equals(3)) {
//                holder.CartProductPrice.setText(cartData.getDealerPrice());

                holder.CartProductPrice.setText("₹  ");
                holder.CartProductPrice.append(cartData.getDealerPrice());
            } else if (Variables.departmentId.equals(4)) {
//                holder.CartProductPrice.setText(cartData.getMartPrice());

                holder.CartProductPrice.setText("₹  ");
                holder.CartProductPrice.append(cartData.getMartPrice());
            }

            holder.CartProductQuantity.setText(String.valueOf(cartData.getQuantity()));
            Glide.with(context)
                    .load(cartCategoryModalList.get(position).getAttachment())
                    .placeholder(R.drawable.baseline_circle_24)
                    .into(holder.CartProductImage);

            holder.CartProductDelete.setOnClickListener(v->{
                Loading.show(holder.itemView.getContext());
                DeleteCartDetails deleteCartDetails = new DeleteCartDetails(Deletelistner,cartData.getId());
                deleteCartDetails.HitApi();
            });

            holder.minus.setOnClickListener(v -> {
                Loading.show(holder.itemView.getContext());
                AddToCart addToCart = new AddToCart(String.valueOf(cartData.getProductId()),String.valueOf(1),String.valueOf(cartData.getId()) ,true,listner);
                addToCart.fetchData();
            });
            holder.plus.setOnClickListener(v -> {
                Loading.show(holder.itemView.getContext());
                AddToCart addToCart = new AddToCart(String.valueOf(cartData.getProductId()),String.valueOf(1) ,"" , false , listner);
                addToCart.fetchData();
            });
            if (cartData.getQuantity() <= 1)
            {
                holder.minus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#020202")));
                holder.minus.setClickable(false);
            }
            else if (cartData.getQuantity() >= 2)
            {
                holder.minus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C6E4FF")));
                holder.minus.setClickable(true);
            }
            if (position % 2 == 0) {
                holder.cartlistlayoutbackground.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#92C8F9"))); // Set elevation for even positions.
            } else {
                holder.cartlistlayoutbackground.setElevation(0f); // Remove elevation for odd positions.
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return cartCategoryModalList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatButton plus,minus;

        RelativeLayout cartlistlayoutbackground;

        AppCompatImageButton CartProductDelete;
        public CircleImageView CartProductImage;
        public TextView CartProductName, CartProductSubInformation, CartProductPrice, CartProductQuantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CartProductName = itemView.findViewById(R.id.CartProductName);
            CartProductSubInformation = itemView.findViewById(R.id.CartProductSubInformation);
            CartProductPrice = itemView.findViewById(R.id.CartProductPrice);
            CartProductImage = itemView.findViewById(R.id.CartProductImage);
            plus = itemView.findViewById(R.id.CartProductPlus);
            minus = itemView.findViewById(R.id.CartProductMinus);

            CartProductQuantity = itemView.findViewById(R.id.CartProductQuantity);
            CartProductDelete = itemView.findViewById(R.id.CartProductDelete);

            CartProductName.setSelected(true);
            CartProductSubInformation.setSelected(true);

            cartlistlayoutbackground = itemView.findViewById(R.id.cartlistlayoutbackground);


        }


    }

}
