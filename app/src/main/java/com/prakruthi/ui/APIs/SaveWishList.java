package com.prakruthi.ui.APIs;

import com.prakruthi.ui.Variables;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SaveWishList {

    String product_id;

    String wishlist;
    private SaveWishList.OnSaveWishListDataFetchedListener mListener;

    public SaveWishList(SaveWishList.OnSaveWishListDataFetchedListener mListener, String product_id) {
        this.mListener = mListener;
        this.product_id= product_id;
    }

    public void HitSaveWishListApi(String wishlist){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new SaveWishList.SaveWishListApi());
        this.wishlist = wishlist;
    }

    private class SaveWishListApi implements Runnable {


        @Override
        public void run() {
            //Creating array for parameters
            String[] field = new String[4];
            field[0] = "user_id";
            field[1] = "token";
            field[2] = "product_id";
            field[3] = "wishlist";

            //Creating array for data
            String[] data = new String[4];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
            data[2] = product_id;
            data[3] = wishlist;
            PutData putData = new PutData(Variables.BaseUrl + "saveWishlist", "POST", field, data);

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
    }

    private void handleError(String failed_to_connect_to_server) {
        mListener.OnSaveWishlistApiGivesError(failed_to_connect_to_server);
    }

    private void handleResponse(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            mListener.OnItemSavedToWishlist(jsonObject.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface OnSaveWishListDataFetchedListener {
        void OnItemSavedToWishlist(String message);
        void OnSaveWishlistApiGivesError(String error);
    }

}
