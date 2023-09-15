package com.prakruthi.ui.ui.home.address;

import static com.google.firebase.messaging.Constants.TAG;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prakruthi.R;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.cart.CartFragment;
import com.prakruthi.ui.ui.home.HomeFragment;
import com.prakruthi.ui.ui.productPage.SavePaymentDetailsActivity;
import com.prakruthi.ui.ui.profile.myaddress.MyAddresses;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Address_BottomSheet_Recycler_Adaptor extends RecyclerView.Adapter<Address_BottomSheet_Recycler_Adaptor.ViewHolder> {
    private final List<Address_BottomSheet_Recycler_Adaptor_Model> mList;
    private final Context context;
    private Button addAddressButton;

    public Address_BottomSheet_Recycler_Adaptor(List<Address_BottomSheet_Recycler_Adaptor_Model> list,Context context) {
        mList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_location_bottom_dialog_recycler, parent, false);
        // Check if the mList is empty
        if (mList.isEmpty()) {
            Log.e(TAG, "asdzxfbjhnkml;" );
            // Create the button
            addAddressButton = new Button(parent.getContext());

            // Set button dimensions
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            addAddressButton.setLayoutParams(params);

            // Set button background as transparent
            addAddressButton.setBackgroundColor(Color.TRANSPARENT);

            // Set button text
            addAddressButton.setText("Add Address");

            // Set button click listener
            addAddressButton.setOnClickListener(v -> {
                // Add your desired action here
                parent.getContext().startActivity(new Intent(parent.getContext(), MyAddresses.class));
            });

            // Add the button to the parent view
            parent.addView(addAddressButton);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address_BottomSheet_Recycler_Adaptor_Model model = mList.get(position);
        holder.nameTextView.setText(model.getName());
        holder.addressTextView.setText(model.getAddress());
        if (model.getDefualt() == 0)
        {
            holder.DefualtAddress.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.Grey));
        }
        holder.itemView.setOnClickListener(v -> {
            defaultDeliveryAddressDetails(String.valueOf(model.getId()), context, success -> {
                if (success) {
                    // Do something on success
                    for (int i = 0; i < HomeFragment.addressRecyclerView.getChildCount(); i++) {
                        View itemView = HomeFragment.addressRecyclerView.getChildAt(i);
                        ViewHolder tempHolder = (ViewHolder) HomeFragment.addressRecyclerView.getChildViewHolder(itemView);
                        tempHolder.DefualtAddress.setVisibility(View.GONE);
                        tempHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.Grey));
                    }

                    holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.Secondary_less));
                    holder.DefualtAddress.setVisibility(View.VISIBLE);
                    HomeFragment.HomeAddress.setText(holder.addressTextView.getText());
//                    SavePaymentDetailsActivity.HomeProductPageDetailsBuyNowSavePaymentDetailsAddress.setText(holder.addressTextView.getText());
//                    CartFragment.HomeProductPageDetailsBuyNowSavePaymentDetailsAddress.setText(holder.addressTextView.getText());

                    Variables.address = holder.addressTextView.getText().toString();
                } else {
                    // Do something on failure
                }
            });

        });

        // Check if the mList is empty
        if (mList.isEmpty() && addAddressButton != null) {
            addAddressButton.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView addressTextView;
        private final TextView DefualtAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.choose_location_bottom_dialog_recycler_name);
            addressTextView = itemView.findViewById(R.id.choose_location_bottom_dialog_recycler_address);
            DefualtAddress = itemView.findViewById(R.id.choose_location_bottom_dialog_recycler_Default);
        }
    }

    public static void defaultDeliveryAddressDetails(String id, Context context, OnTaskCompleted listener) {
        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                //Creating array for parameters
                String[] field = new String[4];
                field[0] = "id";
                field[1] = "user_id";
                field[2] = "token";
                field[3] = "is_default";

                //Creating array for data
                String[] data = new String[4];
                data[0] = params[0];
                data[1] = String.valueOf(Variables.id);
                data[2] = Variables.token;
                data[3] = "1";

                //Creating PutData object and executing the request
                PutData putData = new PutData(Variables.BaseUrl + "saveDeliveryAddressDetails", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        Log.e(TAG, result );
                        if (result != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                boolean status = jsonObject.getBoolean("status_code");
                                return status;
                            } catch (JSONException e) {
                                Log.e("JSON", "Error parsing JSON", e);
                                return false;
                            }
                        }
                    }
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                listener.onTaskCompleted(success);
            }
        }.execute(id);
    }
    public interface OnTaskCompleted {
        void onTaskCompleted(boolean success);
    }



}