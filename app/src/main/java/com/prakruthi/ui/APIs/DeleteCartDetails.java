package com.prakruthi.ui.APIs;

import com.prakruthi.ui.Variables;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DeleteCartDetails {

    private OnCartItemDeleteAPiHit mListner;
    private String id;
    public DeleteCartDetails(OnCartItemDeleteAPiHit listner , int id)
    {
        mListner = listner;
        this.id = String.valueOf(id);
    }

    public void HitApi()
    {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new HitDeleteCartAPi());
    }
    private class HitDeleteCartAPi implements Runnable
    {

        @Override
        public void run() {
            //Creating array for parameters
            String[] field = new String[3];
            field[0] = "user_id";
            field[1] = "token";
            field[2] = "id";
            //Creating array for data
            String[] data = new String[3];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
            data[2] = id;
            PutData putData = new PutData(Variables.BaseUrl+"deleteCartDetails", "POST", field, data);
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

    private void handleResponse(String result) {
        mListner.OnCartItemDeleted(result);
    }
    private void handleError(String failed_to_fetch_data) {
        mListner.OnCartItemDeleteAPiGivesError(failed_to_fetch_data);
    }

    public interface OnCartItemDeleteAPiHit
    {
        void OnCartItemDeleted(String message);
        void OnCartItemDeleteAPiGivesError(String error);
    }
}
