package com.prakruthi.ui.APIs;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.UserDetails;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GetUserData {


    private GetUserData.OnGetUserDataFetchedListener mListener;

    public GetUserData(GetUserData.OnGetUserDataFetchedListener listener) {
        mListener = listener;
    }

    public void HitGetUserDataApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new GetUserData.GetUserDataApi());
    }


    private class GetUserDataApi implements Runnable {
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
            PutData putData = new PutData(Variables.BaseUrl + "getUserData", "POST", field, data);

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
                    Log.wtf("GetUserDataApi: ",result );
                    JSONObject jsonResponse = new JSONObject(result);
                    if (jsonResponse.getBoolean("status_code"))
                    {
                        JSONObject UserData = jsonResponse.getJSONObject("data");
                        UserDetails.name = UserData.getString("name");
                        UserDetails.email = UserData.getString("email");
                        UserDetails.city = UserData.getString("city");
                        UserDetails.state = UserData.getString("state");
                        UserDetails.district = UserData.getString("district");
                        mListener.onUserDataFetched();
                    }
                    else
                    {
                        handleError("Internal Error");
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
        mListener.onUserDataFetchError(failed_to_fetch_data);
    }



    public interface OnGetUserDataFetchedListener {

        void onUserDataFetched();

        void onUserDataFetchError(String error);

    }


}
