package com.prakruthi.ui.ui.profile.order_qty;



import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.SaveOrderPurchaseQuantityQtyDataApi;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.OrderPurchaseQuantityDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrdersQtyAdaptor extends RecyclerView.Adapter<OrdersQtyAdaptor.ViewHolder> {

    SaveOrderPurchaseQuantityQtyDataApi.OnSaveOrderPurchaseQuantityQtyDataApiHit listner;
    ArrayList<OrdersQtyModal> ordersQtys;

    private Button addAddressButton;

    public static  JSONArray array=new JSONArray();
        JSONObject json=new JSONObject();
        Context context;
    public OrdersQtyAdaptor(ArrayList<OrdersQtyModal> ordersQtys, Context context) {
        this.ordersQtys = ordersQtys;
        this.listner = listner;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorders_qty_listlayout, parent, false);
        return new ViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {

            OrdersQtyModal ordersQtyModal = ordersQtys.get(position);

            holder.MyOrdersQtyProductName.setText(ordersQtyModal.getName());
//        holder.MyOrdersQtyProductSubInformation.setText(ordersQtyModal.getProduct_id());

            Glide.with(holder.itemView.getContext()).load(ordersQtyModal.getAttachment()).placeholder(R.drawable.baseline_circle_24).into(holder.MyOrdersQtyProductImage);

            holder.QuantityEditText.setText(ordersQtyModal.getQuantity());

            holder.AvtQtyRemainingEditText.setText(ordersQtyModal.getRemaining_quntity());

//            holder.AvtQtyRemainingEditText.setText(OrderPurchaseQuantityDetails.remaining_quntity);

            holder.AvtQtyRemainingEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }
                @Override
                public void onTextChanged(final CharSequence s, int start, int before,
                                          int count) {
//                    try {
//                        json.put( ordersQtyModal.id, ordersQtyModal.getQuantity());
//                        arr.put(json);
//
//                    }catch (Exception e){
//
//                    }
//                hash.put(json);
                }
                @Override
                public void afterTextChanged(final Editable s) {
                    //avoid triggering event when text is too short
                    if (s.length() > 0) {

                        int  sUsername = Integer.parseInt(holder.AvtQtyRemainingEditText.getText().toString());
                        int RemaingQuant= Integer.parseInt(ordersQtyModal.getRemaining_quntity());
                      if (RemaingQuant>sUsername) {
                          try {

                              json.put( ordersQtyModal.id, sUsername);
//                hash.put(json);
                              array.put(json);
//                            context.getSharedPreferences("Login").edit().putString("savedData", arr.toString()).apply();
                          } catch (JSONException exception) {
                              // Do something with exception
                          }

                      }else {
//                           holder.AvtQtyRemainingEditText.setText(ordersQtyModal.getRemaining_quntity());
                            Toast.makeText(context, "Invalid Available Quantity", Toast.LENGTH_SHORT).show();
                        }



                    }
                }
            });

            holder.Update.setOnClickListener(v -> {

                Loading.show(holder.itemView.getContext());



            });


            if (position % 2 == 0) {
                holder.qty_cartlistlayoutbackground.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#92C8F9"))); // Set elevation for even positions.
            } else {
                holder.qty_cartlistlayoutbackground.setElevation(0f); // Remove elevation for odd positions.
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
//        return 0;
        return ordersQtys.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView MyOrdersQtyProductImage;
        TextView MyOrdersQtyProductName, MyOrdersQtyProductSubInformation;
        TextView MyOrdersQtyProductNextBtn, QuantityEditText;
        EditText AvtQtyRemainingEditText;
//        EditText QuantityEditText,AvtQtyRemainingEditText;

        RelativeLayout qty_cartlistlayoutbackground;
        AppCompatButton Update;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            MyOrdersQtyProductImage = itemView.findViewById(R.id.MyOrdersQtyProductImage);
            MyOrdersQtyProductName = itemView.findViewById(R.id.MyOrdersQtyProductName);
            MyOrdersQtyProductSubInformation = itemView.findViewById(R.id.MyOrdersQtyProductSubInformation);
            MyOrdersQtyProductNextBtn = itemView.findViewById(R.id.MyOrdersQtyProductNextBtn);

            QuantityEditText = itemView.findViewById(R.id.QuantityEditText);

            AvtQtyRemainingEditText = itemView.findViewById(R.id.AvtQtyRemainingEditText);

//            AvtQtyRemainingEditText.setSelected(true);

            MyOrdersQtyProductName.setSelected(true);

            Update = itemView.findViewById(R.id.Update);

            qty_cartlistlayoutbackground = itemView.findViewById(R.id.qty_cartlistlayoutbackground);
        }
    }


}

