package com.prakruthi.ui.APIs;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.home.products.HomeProductModel;
import com.prakruthi.ui.ui.profile.myorders.TrackingOrdersModel;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TrackingOrdersApi {

    private OnTrackingOrdersApiHitsListener mListner;

    private String cartId;
//    cart_id

    public void HitTrackingOrdersApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new HitTrackingOrdersApiApis());
    }

    public TrackingOrdersApi(OnTrackingOrdersApiHitsListener mListner, String cartId) {
        this.mListner = mListner;
        this.cartId = cartId;
    }


    private class HitTrackingOrdersApiApis implements Runnable {
        @Override
        public void run() {
            String[] field = new String[3];
            field[0] = "cart_id";
            field[1] = "user_id";
            field[2] = "token";

            String[] data = new String[5];
            data[0] = cartId;
            data[1] = String.valueOf(Variables.id);
            data[2] = Variables.token;

            PutData putData = new PutData(Variables.BaseUrl + "trackingOrders", "POST", field, data);

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

    private void handleError(String error) {
        mListner.OnTrackingOrdersApiGivesError(error);
    }

    private void handleResponse(String result) {
        try {
            JSONObject jsonResponse = new JSONObject(result);
            JSONArray productList = jsonResponse.getJSONArray("result");
            List<TrackingOrdersModel> trackingOrdersModels = new ArrayList<>();
            for (int i = 0; i < productList.length(); i++) {
                JSONObject productlist = productList.getJSONObject(i);
                Boolean Placed = Boolean.valueOf(productlist.getString("Placed"));
                Boolean Confirmed = Boolean.valueOf(productlist.getString("Confirmed"));
                Boolean Dispatched = Boolean.valueOf(productlist.getString("Dispatched"));
                Boolean Arrived = Boolean.valueOf(productlist.getString("Arrived"));
                Boolean Outfordelivery = Boolean.valueOf(productlist.getString("Outfordelivery"));
                trackingOrdersModels.add(new TrackingOrdersModel(Placed, Confirmed, Dispatched, Arrived, Outfordelivery));
            }
            mListner.OnTrackingOrdersApiGivesFetched(trackingOrdersModels);

        } catch (JSONException e) {
            e.printStackTrace();
            try {
                JSONObject jsonObject = new JSONObject(result);
                handleError(jsonObject.getString("message"));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    public interface OnTrackingOrdersApiHitsListener {
        void OnTrackingOrdersApiGivesFetched(List<TrackingOrdersModel> trackingOrdersModel);

        void OnTrackingOrdersApiGivesError(String error);
    }
}