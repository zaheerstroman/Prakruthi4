package com.prakruthi.ui.APIs;

import com.prakruthi.ui.Variables;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DeleteDeliveryAddressDetails {

    private OnDeleteDeliveryAddressApiHits mListner;

    public int id;
//    public String id;

    public DeleteDeliveryAddressDetails(OnDeleteDeliveryAddressApiHits mListner , int id)
    {
        this.mListner = mListner;
        this.id = id;
    }

    public void HitApi()
    {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new HitDeleteDeliveryAddressDetailsApi());
    }


    private class HitDeleteDeliveryAddressDetailsApi implements Runnable {

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
            data[2] = String.valueOf(id);

            PutData putData = new PutData(Variables.BaseUrl+"deleteDeliveryAddressDetails", "POST", field, data);

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
        mListner.OnOnDeleteDeliveryAddressApiGivesError(error);
    }

    private void handleResponse(String message) {
        mListner.OnDeleteDeliveryAddress(message);

    }

    public interface OnDeleteDeliveryAddressApiHits
    {
        void OnDeleteDeliveryAddress(String message);
        void OnOnDeleteDeliveryAddressApiGivesError(String Errpr);
    }
}
