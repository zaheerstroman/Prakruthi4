package com.prakruthi.ui.APIs;

import com.prakruthi.ui.Variables;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserDetailsUpdate {

    private final UserDetailsUpdate.OnUserDetailsUpdateListener mListener;

    String name , email , city , state , district;

    public UserDetailsUpdate(String name, String email, String city, String state, String district , UserDetailsUpdate.OnUserDetailsUpdateListener listener) {
        mListener = listener;
        this.name = name;
        this.email = email;
        this.city = city;
        this.state = state;
        this.district = district;
    }

    public void HitUserDetailsUpdateApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new UserDetailsUpdate.GetWishlistApi());
    }

    private class GetWishlistApi implements Runnable {

        @Override
        public void run() {
            //Creating array for parameters
            String[] field = new String[7];
            field[0] = "user_id";
            field[1] = "token";
            field[2] = "name";
            field[3] = "email";
            field[4] = "city";
            field[5] = "state";
            field[6] = "district";
            //Creating array for data
            String[] data = new String[7];
            data[0] = String.valueOf(Variables.id);
            data[1] = String.valueOf(Variables.token);
            data[2] = name;
            data[3] = email;
            data[4] = city;
            data[5] = state;
            data[6] = district;
            PutData putData = new PutData(Variables.BaseUrl + "userDetailsUpdate", "POST", field, data);

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
                    if (jsonResponse.getBoolean("status_code"))
                    {
                        mListener.onUserDetailsUpdate(jsonResponse.getString("message"));
                    }
                    else
                    {
                        handleError(jsonResponse.getString("message"));
                    }


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
        mListener.onUserDetailsError(failed_to_fetch_data);
    }

    public interface OnUserDetailsUpdateListener {

        void onUserDetailsUpdate(String message);

        void onUserDetailsError(String error);

    }



}
