package com.prakruthi.ui.APIs;

import static com.google.firebase.messaging.Constants.TAG;

import android.content.Context;
import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.cart.CartModal;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GetCartDetails {

    private GetCartDetails.OnDataFetchedListener mListener;

    public GetCartDetails(GetCartDetails.OnDataFetchedListener listener) {
        mListener = listener;
    }

    public void fetchData() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new GetCartDetails.GetCArtData());
    }

    private class GetCArtData implements Runnable {

        @Override
        public void run() {
            //Creating array for parameters
            String[] field = new String[2];
            field[0] = "user_id";
            field[1] = "token";
            //Creating array for data
            String[] data = new String[2];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
            PutData putData = new PutData(Variables.BaseUrl+"getCartDetails", "POST", field, data);

            if (putData.startPut()) {
                String result = putData.getResult();
                Log.e(TAG, result );
                if (putData.onComplete()) {
                    result = putData.getResult();
                    Log.e(TAG, result );
                    handleResponse(result);
                } else {
                    handleError("Failed to fetch data");
                }
            } else {
                handleError("Failed to connect to server");
            }
        }

        private void handleResponse(String result) {
            if (result != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    JSONArray cartList = jsonResponse.getJSONArray("cart_data");
                    CartModal.cartAmount = jsonResponse.getInt("cart_amount");
                    CartModal.subTotal = jsonResponse.getInt("sub_total");

//                    CartModal.tax = jsonResponse.getInt("tax");

                    CartModal.cgst = jsonResponse.getInt("cgst");
                    CartModal.sgst = jsonResponse.getInt("sgst");
                    CartModal.igst = jsonResponse.getInt("igst");

                    CartModal.cgst_percentage = jsonResponse.getInt("cgst_percentage");
                    CartModal.sgst_percentage = jsonResponse.getInt("sgst_percentage");
                    CartModal.igst_percentage = jsonResponse.getInt("igst_percentage");

                    ArrayList<CartModal> cartModal = new ArrayList<>();

                    // Populate lists
                    for (int i = 0; i < cartList.length(); i++) {
                        JSONObject cartList1 = cartList.getJSONObject(i);
                        int id = cartList1.getInt("id");
                        int product_id = cartList1.getInt("product_id");
                        int quantity = cartList1.getInt("quantity");
                        String name = cartList1.getString("name");

                        String customer_price = cartList1.getString("customer_price");
                        String actual_price = cartList1.getString("actual_price");
                        String delar_price = cartList1.getString("delar_price");
                        String mart_price = cartList1.getString("mart_price");

                        String attachment = cartList1.getString("attachment");
                        String description = cartList1.getString("description");
//                        cartModal.add(new CartModal(id,product_id,quantity,name,description,customer_price,attachment));
                        cartModal.add(new CartModal(id,product_id,quantity,name,description,customer_price,actual_price,delar_price,mart_price,attachment));

                    }
                    // Call listener with all three lists
                    mListener.onCartListFetched(cartModal);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handleError("Failed to parse data");
                }
            } else {
                handleError("Failed to fetch data");
            }
        }

        private void handleError(String error) {
            mListener.onDataFetchError(error);
        }
    }


    public interface OnDataFetchedListener {
        void onCartListFetched(ArrayList<CartModal> cartModals);
        void onDataFetchError(String error);
    }

}
