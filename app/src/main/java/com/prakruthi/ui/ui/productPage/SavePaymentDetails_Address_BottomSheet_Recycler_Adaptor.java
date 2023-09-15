package com.prakruthi.ui.ui.productPage;

import static com.google.firebase.messaging.Constants.TAG;
import static com.prakruthi.ui.ui.AddressUserDetails.address;
import static com.prakruthi.ui.ui.AddressUserDetails.city;
import static com.prakruthi.ui.ui.AddressUserDetails.country;
import static com.prakruthi.ui.ui.AddressUserDetails.postal_code;
import static com.prakruthi.ui.ui.AddressUserDetails.state;

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
import com.prakruthi.ui.ui.home.HomeFragment;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model;
import com.prakruthi.ui.ui.profile.myaddress.MyAddresses;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SavePaymentDetails_Address_BottomSheet_Recycler_Adaptor extends RecyclerView.Adapter<SavePaymentDetails_Address_BottomSheet_Recycler_Adaptor.ViewHolder> {
    private final List<Address_BottomSheet_Recycler_Adaptor_Model> mList;

    private final Context context;
    private Button addAddressButton;

    public SavePaymentDetails_Address_BottomSheet_Recycler_Adaptor(List<Address_BottomSheet_Recycler_Adaptor_Model> list, Context context) {
        mList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SavePaymentDetails_Address_BottomSheet_Recycler_Adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_location_bottom_dialog_recycler, parent, false);
        // Check if the mList is empty
        if (mList.isEmpty()) {
            Log.e(TAG, "asdzxfbjhnkml;");
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
        return new SavePaymentDetails_Address_BottomSheet_Recycler_Adaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavePaymentDetails_Address_BottomSheet_Recycler_Adaptor.ViewHolder holder, int position) {
        Address_BottomSheet_Recycler_Adaptor_Model model = mList.get(position);
        holder.nameTextView.setText(model.getName());
        holder.addressTextView.setText(model.getAddress());
        if (model.getDefualt() == 0) {
            holder.DefualtAddress.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.Grey));
        }
        holder.itemView.setOnClickListener(v -> {
            defaultDeliveryAddressDetails(String.valueOf(model.getId()), context, success -> {
                if (success) {
                    // Do something on success
                    for (int i = 0; i < SavePaymentDetailsActivity.addressRecyclerView_SavePaymentDetails.getChildCount(); i++) {
                        View itemView = SavePaymentDetailsActivity.addressRecyclerView_SavePaymentDetails.getChildAt(i);
                        SavePaymentDetails_Address_BottomSheet_Recycler_Adaptor.ViewHolder tempHolder = (SavePaymentDetails_Address_BottomSheet_Recycler_Adaptor.ViewHolder)
                                SavePaymentDetailsActivity.addressRecyclerView_SavePaymentDetails.getChildViewHolder(itemView);
                        tempHolder.DefualtAddress.setVisibility(View.GONE);
                        tempHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.Grey));
                    }

                    holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.Secondary_less));
                    holder.DefualtAddress.setVisibility(View.VISIBLE);
                    HomeFragment.HomeAddress.setText(holder.addressTextView.getText());
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

    public static void defaultDeliveryAddressDetails(String id, Context context, SavePaymentDetails_Address_BottomSheet_Recycler_Adaptor.OnTaskCompleted listener) {
        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {

                //Creating array for parameters
                String[] field = new String[4];
                field[0] = "id";
                field[1] = "user_id";
                field[2] = "token";
                field[3] = "is_default";

//                //Creating array for parameters
//                String[] field = new String[10];
//                field[0] = "id";
//                field[1] = "user_id";
//                field[2] = "token";
//                field[3] = "name";
//                field[4] = "city";
//                field[5] = "state";
//                field[6] = "country";
//                field[7] = "address";
//                field[8] = "postal_code";
//                field[9] = "is_default";

                          //Creating array for data
                                String[] data = new String[4];
                                data[0] = params[0];
                                data[1] = String.valueOf(Variables.id);
                                data[2] = Variables.token;
                                data[3] = "1";

//                //Creating array for data
//                String[] data = new String[10];
//                data[0] = params[0];
//                data[1] = String.valueOf(Variables.id);
//                data[2] = Variables.token;
//                field[3] = name;
//                field[4] = city;
//                field[5] = state;
//                field[6] = country;
//                field[7] = address;
//                field[8] = postal_code;
//                data[9] = "1";

                //Creating PutData object and executing the request
                PutData putData = new PutData(Variables.BaseUrl + "saveDeliveryAddressDetails", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        Log.e(TAG, result);
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