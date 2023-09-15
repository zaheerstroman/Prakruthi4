package com.prakruthi.ui.APIs;

import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SaveDeliveryAddressDetails {

    private final OnSaveDeliveryAddressApiHits mListener;

    public String name;
    public String city;
    public String state;
    public String country;
    public String address;
    public String postal_code;
    public int is_default;

    public SaveDeliveryAddressDetails(OnSaveDeliveryAddressApiHits mListener, String name, String city, String state, String country, String address, String postal_code, int is_default) {
        this.mListener = mListener;
        this.name = name;
        this.city = city;
        this.state = state;
        this.country = country;
        this.address = address;
        this.postal_code = postal_code;
        this.is_default = is_default;
    }

    public void HitApi()
    {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new HitSaveDeliveryAddressDetailsApi());
    }

    private class HitSaveDeliveryAddressDetailsApi implements Runnable {

        @Override
        public void run() {
            // Creating array for parameters
            String[] field = new String[9];
            field[0] = "user_id";
            field[1] = "token";
            field[2] = "name";
            field[3] = "city";
            field[4] = "state";
            field[5] = "country";
            field[6] = "address";
            field[7] = "postal_code";
            field[8] = "is_default";

            // Creating array for data
            String[] data = new String[9];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
            data[2] = name;
            data[3] = city;
            data[4] = state;
            data[5] = country;
            data[6] = address;
            data[7] = postal_code;
            data[8] = String.valueOf(is_default);

            Log.e(TAG, Arrays.toString(field)+Arrays.toString(data));
            PutData putData = new PutData(Variables.BaseUrl + "saveDeliveryAddressDetails", "POST", field, data);
            if (putData.startPut()) {
                String result = putData.getResult();
                Log.e(TAG,result);
                if (putData.onComplete()) {
                    result = putData.getResult();
                    Log.e(TAG,result);
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
                handleError("Failed to connect to the server");
            }
        }
    }

    private void handleError(String error) {
        mListener.onSaveDeliveryAddressApiError(error);
    }

    private void handleResponse(String message) {
        mListener.onSaveDeliveryAddress(message);
    }

    public interface OnSaveDeliveryAddressApiHits {
        void onSaveDeliveryAddress(String message);
        void onSaveDeliveryAddressApiError(String error);
    }
}