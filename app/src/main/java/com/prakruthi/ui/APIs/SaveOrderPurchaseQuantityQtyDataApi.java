package com.prakruthi.ui.APIs;

import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;
import static com.prakruthi.ui.Variables.productId;
import static com.prakruthi.ui.Variables.quntity;


import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.profile.order_qty.OrdersQtyAdaptor;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SaveOrderPurchaseQuantityQtyDataApi {

    private final OnSaveOrderPurchaseQuantityQtyDataApiHit mListner;

    public String remaining_quntity;

    public SaveOrderPurchaseQuantityQtyDataApi(OnSaveOrderPurchaseQuantityQtyDataApiHit mListner, String remaining_quntity) {
        this.mListner = mListner;
        this.remaining_quntity = remaining_quntity;
    }



    public void HitSaveOrderPurchaseQuantityQtyDataApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new HitSaveOrderPurchaseQuantityQtyDataApiApi());
    }

    private class HitSaveOrderPurchaseQuantityQtyDataApiApi implements Runnable {

        @Override
        public void run() {
//            JSONObject filters = new JSONObject();
            JSONObject updateDatas = new JSONObject();

            try {

//                filters.put("updateData", updateData);

                updateDatas.put("product_id", productId);
                updateDatas.put("quntity", quntity);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            //Creating array for parameters
            String[] field = new String[3];
            field[0] = "user_id";
            field[1] = "token";
            field[2] = "update_data";

            //Creating array for data
            String[] data = new String[3];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
//            data[2] = updateData;
            try {
                data[2] = String.valueOf(OrdersQtyAdaptor.array.get(0).toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            Log.e(TAG, Arrays.toString(field) + Arrays.toString(data));
            PutData putData = new PutData(Variables.BaseUrl + "saveOrderData", "POST", field, data);

            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    Log.e(TAG, result);
                    if (putData.onComplete()) {
                        result = putData.getResult();
                        Log.e(TAG, result);
                        try {
                            JSONObject response = new JSONObject(result);
                            // Extract the "message" string
                            String message = response.getString("message");

                            handleResponse(message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                    handleResponse(result);
                    } else {
                        handleError("Failed to fetch data");
                    }
                } else {
                    handleError("Failed to connect to server");
                }
            }

        }
    }


    private void handleResponse(String messaage) {
        mListner.OnSaveOrderPurchaseQuantityQtyDataApiHitSuc(messaage);

    }

    private void handleError(String error) {
        mListner.OnSaveOrderPurchaseQuantityQtyDataApiHitError(error);

    }

    public interface OnSaveOrderPurchaseQuantityQtyDataApiHit {

        //        void OnSearchResult(List<SearchModle> product);
        void OnSaveOrderPurchaseQuantityQtyDataApiHitSuc(String messaage);
//void OnSaveOrderPurchaseQuantityQtyDataApiHitSuc(String product);

        void OnSaveOrderPurchaseQuantityQtyDataApiHitError(String error);

    }
}

