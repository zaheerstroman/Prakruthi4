//package com.prakruthi.ui.APIs;
//
//import static com.google.firebase.messaging.Constants.TAG;
//
//import android.util.Log;
//
//import com.prakruthi.ui.Variables;
//import com.prakruthi.ui.ui.profile.ProfileGetUserDataResponse;
//import com.vishnusivadas.advanced_httpurlconnection.PutData;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//public class GetUserDataApi {
//
//    private OnGetUserDataApiHitFetchedListner mListner;
//
//
//    public GetUserDataApi(OnGetUserDataApiHitFetchedListner mListner) {
//        this.mListner = mListner;
//    }
//
//    public void HitGetUserDataApi() {
//        Executor executor = Executors.newSingleThreadExecutor();
//        executor.execute(new UserDataApi());
//    }
//
//    private class UserDataApi implements Runnable {
//
//
//        @Override
//        public void run() {
//            //Creating array for parameters
//            String[] field = new String[2];
//            field[0] = "user_id";
//            field[1] = "token";
//
//            //Creating array for data
//            String[] data = new String[2];
////            data[0] = String.valueOf(Variables.id);
//            data[0] = String.valueOf(Variables.userId);
//            data[1] = Variables.token;
//
//            PutData putData = new PutData(Variables.BaseUrl + "getUserData", "POST", field, data);
//
//            if (putData.startPut()) {
//                if (putData.onComplete()) {
////                    String result = putData.getResult();
//                    String response = putData.getResult();
//
//                    //----------------------------
////                    handleResponse(response);
//
////                    Log.e(TAG, result );
//                    Log.e(TAG, response);
////                    if (response != null) {
//                        try {
//                            // Parse JSON response
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONArray jsonArray = jsonObject.getJSONArray("data");
//
////                        ArrayList<RecentProductModel> recentProductModels = new ArrayList<>();
////                        List<ProfileGetUserDataResponse.ProfileGetUserDataModel> profileGetUserDataModels = new List<>();
////                        List<ProfileGetUserDataResponse.ProfileGetUserDataModel> profileGetUserDataModels = new ArrayList<>();
//                            ArrayList<ProfileGetUserDataResponse.ProfileGetUserDataModel> profileGetUserDataModels = new ArrayList<>();
//
//                            // Loop through JSON array and create Address objects
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject obj = jsonArray.getJSONObject(i);
//
//                            boolean statusCode = jsonObject.getBoolean("status_code");
//                            String message = jsonObject.getString("message");
//                            String privacyPolicy = jsonObject.getString("privacyPolicy");
//                            String termsAndConditions = jsonObject.getString("termsAndConditions");
//
////                                String status_code = ResultJson.getString("status_code");
//
//                                String returnRefundPolicy = jsonObject.getString("returnRefundPolicy");
//                                String security = jsonObject.getString("security");
//                                String aboutUs = jsonObject.getString("aboutUs");
////                                String message = jsonObject.getString("message");
////
////                            if (statusCode)
////                            {
////                                handleResponse("Thanks For Your Feedback");
////                            }
////                            else
////                            {
////                                handleResponse("Internal Error");
////                            }
//
////                            int id = obj.getInt("id");
//                            String id = obj.getString("id");
////                            int department_id = obj.getInt("department_id");
//                            String department_id = obj.getString("department_id");
//                                String user_code = obj.getString("user_code");
//
//                                String name = obj.getString("name");
//                            String last_name = obj.getString("last_name");
//                                String email = obj.getString("email");
//                            String password = obj.getString("password");
//                                String mobile = obj.getString("mobile");
//                            String gender = obj.getString("gender");
//                            String dob = obj.getString("dob");
//                            String attachment = obj.getString("attachment");
//                                String city = obj.getString("city");
//                            String postCode = obj.getString("postCode");
//                            String address = obj.getString("address");
//                                String state = obj.getString("state");
//                            String country = obj.getString("country");
//                            String district = obj.getString("district");
//                            String street = obj.getString("street");
//                            String about = obj.getString("about");
//                            String status = obj.getString("status");
//                            String mobile_verified = obj.getString("mobile_verified");
//                            String is_verified = obj.getString("is_verified");
//                            String log_date_created = obj.getString("log_date_created");
//                            String created_by = obj.getString("created_by");
//                            String log_date_modified = obj.getString("log_date_modified");
//                            String modified_by = obj.getString("modified_by");
//                            String fcm_token = obj.getString("fcm_token");
//                            String device_id = obj.getString("device_id");
//                            String allow_email = obj.getString("allow_email");
//                            String allow_sms = obj.getString("allow_sms");
//                            String allow_push = obj.getString("allow_push");
//
////                            int Defualt = obj.getInt("is_default");
//
////                            int id = obj.getInt("id");
//
////                            if (Defualt == 1) {
////                                Variables.address = address;
////                            }
////                            Boolean statusCode, String privacyPolicy, String termsAndConditions, String message, int id, Integer departmentId, String userCode, String name, String lastName, String email, String password, String mobile, String gender, String dob, String attachment, String city, String postCode, String address, String state, String country, String district, String street, String about, String status, String mobileVerified, String isVerified, String logDateCreated, String createdBy, String logDateModified, String modifiedBy, String fcmToken, String deviceId, String allowEmail, String allowSms, String allowPush
////                            profileGetUserDataList.add(new ProfileGetUserDataResponse(id, departmentId, userCode, name, lastName, email, password, mobile, gender, dob, attachment, city, postCode, address, state, country, district, street, about, status, mobileVerified, isVerified, logDateCreated, createdBy, logDateModified, modifiedBy, fcmToken, deviceId, allowEmail, allowSms, allowPush));
//                            profileGetUserDataModels.add(new ProfileGetUserDataResponse.ProfileGetUserDataModel(id, department_id, user_code, name, last_name, email, password, mobile, gender, dob, attachment, city, postCode, address, state, country, district, street, about, status, mobile_verified, is_verified, log_date_created, created_by, log_date_modified, modified_by, fcm_token, device_id, allow_email, allow_sms, allow_push));
////                                profileGetUserDataModels.add(new ProfileGetUserDataResponse.ProfileGetUserDataModel(user_code, name, email, mobile, city, state, district));
//
//                            }
////                        mListner.OnGetUserDataResultApiGivesSuccess(profileGetUserDataList);
//                            mListner.OnGetUserDataResultApiGivesSuccess(profileGetUserDataModels);
//
//
////                        handleResponseAdd(message);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
////                        handleError("Internal Error");
////                            mListner.OnGetUserDataResultApiGivesError("Internal Error");
//
//                        }
////                    }
//                    handleResponse(response);
//                } else {
//                    handleError("Failed to fetch data");
//                }
//            } else {
//                handleError("Failed to connect to server");
//            }
//
//        }
////                return profileGetUserDataList;
//    }
//
//    private void handleResponse(String result) {
//        if (result != null) {
//            try {
//
//
////            JSONObject response = new JSONObject(result);
//                JSONObject jsonResponse = new JSONObject(result);
//
//                JSONArray getUrerDataList = jsonResponse.getJSONArray("data");
//
//                boolean statusCode = jsonResponse.optBoolean("status_code");
//                String privacyPolicy = jsonResponse.optString("privacyPolicy");
//                String termsAndConditions = jsonResponse.optString("termsAndConditions");
//                String returnRefundPolicy = jsonResponse.optString("returnRefundPolicy");
//                String security = jsonResponse.optString("security");
//                String aboutUs = jsonResponse.optString("aboutUs");
//                String message = jsonResponse.optString("message");
//
//                String user_code = jsonResponse.getString("user_code");
//                String name = jsonResponse.getString("name");
//                String email = jsonResponse.getString("email");
//                String mobile = jsonResponse.getString("mobile");
//                String city = jsonResponse.getString("city");
//                String state = jsonResponse.getString("state");
//
//
//
//
////                String user_code = jsonResponse.getString("user_code");
////                String name = jsonResponse.getString("name");
////                String email = jsonResponse.getString("email");
////                String mobile = jsonResponse.getString("mobile");
////                String city = jsonResponse.getString("city");
////                String state = jsonResponse.getString("state");
//
////                JSONArray getUrerDataList = jsonResponse.getJSONArray("data");
////            List<ProfileGetUserDataResponse.ProfileGetUserDataModel> profileGetUserDataModels = new ArrayList<>();
//                List<ProfileGetUserDataResponse> profileGetUserDataResponses = new ArrayList<>();
//
//                for (int i = 0; i < getUrerDataList.length(); i++) {
//                    JSONObject product = getUrerDataList.getJSONObject(i);
//
////                boolean statusCode1 = product.getBoolean("status_code");
////                String message1 = product.getString("message");
////                String privacyPolicy1 = product.getString("privacyPolicy");
////                String termsAndConditions1 = product.getString("termsAndConditions");
//
////                boolean statusCode = product.getBoolean("status_code");
////                String message = product.getString("message");
////                String privacyPolicy = product.getString("privacyPolicy");
////                String termsAndConditions = product.getString("termsAndConditions");
//
////                    String user_code = jsonResponse.getString("user_code");
////                    String name = jsonResponse.getString("name");
////                    String email = jsonResponse.getString("email");
////                    String mobile = jsonResponse.getString("mobile");
////                    String city = jsonResponse.getString("city");
////                    String state = jsonResponse.getString("state");
//
//                    profileGetUserDataResponses.add(new ProfileGetUserDataResponse(user_code, name, email, mobile, city, state));
//
////                    profileGetUserDataResponses.add(new ProfileGetUserDataResponse(statusCode, privacyPolicy, termsAndConditions, returnRefundPolicy,security,aboutUs,message));
////                    profileGetUserDataResponses.add(new ProfileGetUserDataResponse(statusCode, message, privacyPolicy, termsAndConditions));
////                    profileGetUserDataResponses.add(new ProfileGetUserDataResponse.ProfileGetUserDataModel(user_code, name, email, mobile,city,state));
//
//                }
//
////            mListener.OnItemSavedToWishlist(jsonObject.getString("message"));
////            mListner.OnGetUserDataResultApiGivesSuccess(jsonResponse.getString("message"));
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//                // Handle JSON parsing error
////            mListner.OnGetUserDataResultApiGivesSuccess("JSON parsing error");
//            }
//        }
//    }
//
//
//    private void handleError(String error) {
//        mListner.OnGetUserDataResultApiGivesError(error);
//
//    }
//
//
//    public interface OnGetUserDataApiHitFetchedListner {
//
////        void OnGetUserDataResultApiGivesSuccess(String profileGetUserDataModels);
//        void OnGetUserDataResultApiGivesSuccess(ArrayList<ProfileGetUserDataResponse.ProfileGetUserDataModel> profileGetUserDataModels);
//
//        void OnGetUserDataResultApiGivesError(String error);
//    }
//}

//---------------------------------------------

package com.prakruthi.ui.APIs;

import static com.google.firebase.messaging.Constants.TAG;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.profile.ProfileGetUserDataResponse;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GetUserDataApi {

    private OnGetUserDataApiHitFetchedListner mListner;


    public GetUserDataApi(OnGetUserDataApiHitFetchedListner mListner) {
        this.mListner = mListner;
    }

    public void HitGetUserDataApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new UserDataApi());
    }

    private class UserDataApi implements Runnable {


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
                    String response = putData.getResult();

                    Log.e(TAG, response);
//                    if (response != null) {
                    try {

                        Log.wtf("GetUserDataApi: ",response );

                        // Parse JSON response
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        ArrayList<ProfileGetUserDataResponse.ProfileGetUserDataModel> profileGetUserDataModels = new ArrayList<>();

                        // Loop through JSON array and create Address objects
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            boolean statusCode = jsonObject.getBoolean("status_code");
                            String message = jsonObject.getString("message");
                            String privacyPolicy = jsonObject.getString("privacyPolicy");
                            String termsAndConditions = jsonObject.getString("termsAndConditions");

                            String returnRefundPolicy = jsonObject.getString("returnRefundPolicy");
                            String security = jsonObject.getString("security");
                            String aboutUs = jsonObject.getString("aboutUs");

                            String id = obj.getString("id");
//                            int department_id = obj.getInt("department_id");
                            String department_id = obj.getString("department_id");
                            String user_code = obj.getString("user_code");

                            String name = obj.getString("name");
                            String last_name = obj.getString("last_name");
                            String email = obj.getString("email");
                            String password = obj.getString("password");
                            String mobile = obj.getString("mobile");
                            String gender = obj.getString("gender");
                            String dob = obj.getString("dob");
                            String attachment = obj.getString("attachment");
                            String city = obj.getString("city");
                            String postCode = obj.getString("postCode");
                            String address = obj.getString("address");
                            String state = obj.getString("state");
                            String country = obj.getString("country");
                            String district = obj.getString("district");
                            String street = obj.getString("street");
                            String about = obj.getString("about");
                            String status = obj.getString("status");
                            String mobile_verified = obj.getString("mobile_verified");
                            String is_verified = obj.getString("is_verified");
                            String log_date_created = obj.getString("log_date_created");
                            String created_by = obj.getString("created_by");
                            String log_date_modified = obj.getString("log_date_modified");
                            String modified_by = obj.getString("modified_by");
                            String fcm_token = obj.getString("fcm_token");
                            String device_id = obj.getString("device_id");
                            String allow_email = obj.getString("allow_email");
                            String allow_sms = obj.getString("allow_sms");
                            String allow_push = obj.getString("allow_push");

                            profileGetUserDataModels.add(new ProfileGetUserDataResponse.ProfileGetUserDataModel(id, department_id, user_code, name, last_name, email, password, mobile, gender, dob, attachment, city, postCode, address, state, country, district, street, about, status, mobile_verified, is_verified, log_date_created, created_by, log_date_modified, modified_by, fcm_token, device_id, allow_email, allow_sms, allow_push));

                        }
                        mListner.OnGetUserDataResultApiGivesSuccess(profileGetUserDataModels);


                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                    handleResponse(response);
                } else {
                    handleError("Failed to fetch data");
                }
            } else {
                handleError("Failed to connect to server");
            }

        }
    }

    private void handleResponse(String result) {
        if (result != null) {
            try {

                Log.wtf("GetUserDataApi: ",result );

                JSONObject jsonResponse = new JSONObject(result);

                JSONArray getUrerDataList = jsonResponse.getJSONArray("data");

                boolean statusCode = jsonResponse.optBoolean("status_code");
                String privacyPolicy = jsonResponse.optString("privacyPolicy");
                String termsAndConditions = jsonResponse.optString("termsAndConditions");
                String returnRefundPolicy = jsonResponse.optString("returnRefundPolicy");
                String security = jsonResponse.optString("security");
                String aboutUs = jsonResponse.optString("aboutUs");
                String message = jsonResponse.optString("message");

                String user_code = jsonResponse.getString("user_code");
                String name = jsonResponse.getString("name");
                String email = jsonResponse.getString("email");
                String mobile = jsonResponse.getString("mobile");
                String city = jsonResponse.getString("city");
                String state = jsonResponse.getString("state");
                String district = jsonResponse.getString("district");

                List<ProfileGetUserDataResponse> profileGetUserDataResponses = new ArrayList<>();

                for (int i = 0; i < getUrerDataList.length(); i++) {
                    JSONObject product = getUrerDataList.getJSONObject(i);


                    profileGetUserDataResponses.add(new ProfileGetUserDataResponse(user_code, name, email, mobile, city, state,district));


                }


            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }


    private void handleError(String error) {
        mListner.OnGetUserDataResultApiGivesError(error);

    }


    public interface OnGetUserDataApiHitFetchedListner {

        //        void OnGetUserDataResultApiGivesSuccess(String profileGetUserDataModels);
        void OnGetUserDataResultApiGivesSuccess(ArrayList<ProfileGetUserDataResponse.ProfileGetUserDataModel> profileGetUserDataModels);

        void OnGetUserDataResultApiGivesError(String error);
    }
}

