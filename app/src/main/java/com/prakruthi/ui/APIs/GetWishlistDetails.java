package com.prakruthi.ui.APIs;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.wishlist.WishListModal;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GetWishlistDetails {

    private GetWishlistDetails.OnWishlistDataFetchedListener mListener;

    public GetWishlistDetails(GetWishlistDetails.OnWishlistDataFetchedListener listener) {
        mListener = listener;
    }

    public void HitWishlistApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new GetWishlistDetails.GetWishlistApi());
    }


    private class GetWishlistApi implements Runnable {

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
            PutData putData = new PutData(Variables.BaseUrl + "getWishlist", "POST", field, data);

            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
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

                    JSONArray wishList = jsonResponse.getJSONArray("result");

                    ArrayList<WishListModal> wishlistModal = new ArrayList<>();

                    // Populate lists
                    for (int i = 0; i < wishList.length(); i++) {
                        JSONObject wishList1 = wishList.getJSONObject(i);
                        int wishlist_id = wishList1.getInt("wishlist_id");

                        String name = wishList1.getString("name");
                        String description = wishList1.getString("description");

                        String customer_price = wishList1.getString("customer_price");
                        String actual_price = wishList1.getString("actual_price");
                        String delar_price = wishList1.getString("delar_price");
                        String mart_price = wishList1.getString("mart_price");

                        String color = wishList1.getString("color");
                        String size = wishList1.getString("size");
                        String type = wishList1.getString("type");
                        String attachment = wishList1.getString("attachment");
                        String product_id = wishList1.getString("product_id");
                        String date = wishList1.getString("date");

                        wishlistModal.add(new WishListModal(wishlist_id, name, description, customer_price,actual_price,delar_price,mart_price, color, size, type, attachment, product_id, date));
                    }
                    // Call listener with all three lists
                    mListener.onWishListFetched(wishlistModal);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handleError("Failed to parse data");
                }
            } else {
                handleError("Failed to fetch data");
            }


        }

    }

    private void handleError(String failed_to_fetch_data) {
        mListener.onDataFetchError(failed_to_fetch_data);
    }


    public interface OnWishlistDataFetchedListener {

        void onWishListFetched(ArrayList<WishListModal> wishlistModal);

        void onDataFetchError(String error);

    }
}