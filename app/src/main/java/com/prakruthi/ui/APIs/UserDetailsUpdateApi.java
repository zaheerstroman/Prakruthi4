package com.prakruthi.ui.APIs;

import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.home.products.HomeProductModel;
import com.prakruthi.ui.ui.profile.EditProfileUpdateDrailsUpdateModels;
import com.prakruthi.ui.ui.profile.ProfileGetUserDataResponse;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserDetailsUpdateApi {
    private final UserDetailsUpdateApi.OnUserDetailsUpdateApiGivesFetchedListner mListner;

    public static Dialog HitFeedbackAPi;
    Context context;


    public String user_id,token,name, email, city, state, district;


    public UserDetailsUpdateApi( String name, String email, String city, String state, String district, String user_id, String token, OnUserDetailsUpdateApiGivesFetchedListner listner) {
        mListner = listner;
        this.name = name;
        this.email = email;
        this.city = city;
        this.state = state;
        this.district = district;
    }




    public void HitUserDetailsUpdateApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new HitEPAPi());
    }

    private class HitEPAPi implements Runnable {
        @Override
        public void run() {
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
            data[1] = Variables.token;
            data[2] = name;
            data[3] = email;
            data[4] = city;
            data[5] = state;
            data[6] = district;

            Log.e(TAG, Arrays.toString(field) + Arrays.toString(data));

            PutData putData = new PutData(Variables.BaseUrl + "userDetailsUpdate", "POST", field, data);

            if (putData.startPut()) {
                String result = putData.getResult();
                Log.e(TAG, result);
                if (putData.onComplete()) {
                    result = putData.getResult();
                    //End ProgressBar (Set visibility to GONE)
                    Log.e(TAG, result);
                    Log.i("PutData", result);
                    handleResponse(result);

                    if (result != null) {


                        try {
                            JSONObject response = new JSONObject(result);

                            // Extract the "message" string
                            String message = response.getString("message");
                            boolean status = response.getBoolean("status_code");

                            boolean statusCode = response.getBoolean("status_code");

                            if (statusCode) {
                                handleResponse("Thanks For Editing Profile");
                            } else {
                                handleResponse("Internal Error");
                            }

                            // Use the "message" string as needed
                            handleResponse(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSON", "Error parsing JSON", e);
//                            return false;

                        }
                    }

                } else {
                    handleError("Failed to fetch data");
                }
            }

            else {
                handleError("Failed to connect to server");
            }

        }
    }

    private void handleError(String error) {
        mListner.OnUserDetailsUpdateApiGivesError(error);
    }




    private void handleResponse(String result) {
        if (result != null) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                if (jsonResponse.getBoolean("status_code"))
                {
                    mListner.OnUserDetailsUpdateApiGivesFetched(jsonResponse.getString("message"));
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



    public interface OnUserDetailsUpdateApiGivesFetchedListner {
        void OnUserDetailsUpdateApiGivesFetched(String message);

        void OnUserDetailsUpdateApiGivesError(String error);
    }
}


