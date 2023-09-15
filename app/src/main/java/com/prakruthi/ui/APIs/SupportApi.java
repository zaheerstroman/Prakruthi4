package com.prakruthi.ui.APIs;

import static com.google.firebase.messaging.Constants.TAG;
import static com.prakruthi.ui.Variables.city;
import static com.prakruthi.ui.Variables.district;
import static com.prakruthi.ui.Variables.email;
import static com.prakruthi.ui.Variables.name;
import static com.prakruthi.ui.Variables.state;
import static com.prakruthi.ui.Variables.token;
import static com.prakruthi.ui.Variables.userId;

import android.util.Log;

import com.prakruthi.ui.Variables;
//import com.prakruthi.ui.ui.profile.ProfileGetUserDataResponse;
import com.prakruthi.ui.ui.profile.ProfileSupportResponse;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SupportApi {

    OnSupportApiHitFetchedListner mListner;

    public SupportApi(OnSupportApiHitFetchedListner mListner) {
        this.mListner = mListner;
    }

    public void HitSupportApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new SupportDataApi());
    }


    private class SupportDataApi implements Runnable {

        @Override
        public void run() {
    //Creating array for parameters
            String[] field = new String[2];
            field[0] = "user_id";
            field[1] = "token";

            //Creating array for data
            String[] data = new String[2];
            data[0] = String.valueOf(Variables.id);
//            data[0] = String.valueOf(userId);
            data[1] = Variables.token;

            PutData putData = new PutData(Variables.BaseUrl + "support", "POST", field, data);

            if (putData.startPut()) {
                if (putData.onComplete()) {

                    String result = putData.getResult();

                    Log.e(TAG, result);

                    try {
                        // Parse JSON response
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("result");

                        ArrayList<ProfileSupportResponse.ProfileSupportModel> profileSupportModels = new ArrayList<>();

                        // Loop through JSON array and create Address objects
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            boolean statusCode = obj.getBoolean("status_code");
                            String message = obj.getString("message");

                            if (statusCode)
                            {
                                handleResponse("Thanks For Your Feedback");
                            }
                            else
                            {
                                handleResponse("Internal Error");
                            }

                            String id = obj.getString("id");

                            String mobile = obj.getString("mobile");

                            String email = obj.getString("email");

                            String address = obj.getString("address");


                            profileSupportModels.add(new ProfileSupportResponse.ProfileSupportModel(id, mobile,email, address));

                        }
                        mListner.OnSupportApiGivesSuccess(profileSupportModels);


                    } catch (JSONException e) {
                        e.printStackTrace();


                    }
//                    }
                    handleResponse(result);
                } else {
                    handleError("Failed to fetch data");
                }
            }

        }

    }

    private void handleResponse(String result) {
        if (result != null) {
            try {

//            JSONObject response = new JSONObject(result);
                JSONObject jsonResponse = new JSONObject(result);

                JSONArray getSupportDataList = jsonResponse.getJSONArray("result");

                boolean statusCode = jsonResponse.optBoolean("status_code");

                String message = jsonResponse.optString("message");


                List<ProfileSupportResponse> profileSupportResponses = new ArrayList<>();

                for (int i = 0; i < getSupportDataList.length(); i++) {
                    JSONObject product = getSupportDataList.getJSONObject(i);


                    profileSupportResponses.add(new ProfileSupportResponse(statusCode,message));

                }


            } catch (JSONException e) {
                e.printStackTrace();

            }
        }

    }

    private void handleError(String error) {
        mListner.OnSupportApiGivesError(error);

    }

    public interface OnSupportApiHitFetchedListner {

        void OnSupportApiGivesSuccess(ArrayList<ProfileSupportResponse.ProfileSupportModel> profileSupportModels);

        void OnSupportApiGivesError(String error);
    }


}
