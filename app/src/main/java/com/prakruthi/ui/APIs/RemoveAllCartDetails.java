package com.prakruthi.ui.APIs;

import com.prakruthi.ui.Variables;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RemoveAllCartDetails {

    private final RemoveAllCartDetails.OnCartItemRemoveAllAPiHit mListner;

    public RemoveAllCartDetails(RemoveAllCartDetails.OnCartItemRemoveAllAPiHit listner) {
        mListner = listner;
    }

    public void RemoveAllHitApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new HitRemoveAllCartAPi());
    }

    private class HitRemoveAllCartAPi implements Runnable {
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
            PutData putData = new PutData(Variables.BaseUrl + "removeAllCartDetails", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    try {
                        JSONObject response = new JSONObject(result);

                        // Extract the "message" string
                        String message = response.getString("message");

                        // Use the "message" string as needed
                        handleResponse(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    handleError("Failed to fetch data");
                }
            } else {
                handleError("Failed to connect to server");
            }
        }


    }

    private void handleError(String error) {
        mListner.OnCartItemRemoveAllAPiGivesError(error);

    }

    private void handleResponse(String message) {
        mListner.OnCartItemRemoveAll(message);
    }


    public interface OnCartItemRemoveAllAPiHit {
        void OnCartItemRemoveAll(String message);
//        void OnCartItemRemovedAll(String message);

        void OnCartItemRemoveAllAPiGivesError(String error);
    }

}