package com.prakruthi.ui.network;

//import static com.prakruthi.ui.ui.productPage.SavePaymentDetailsActivity.message;
//import static com.prakruthi.ui.ui.productPage.SavePaymentDetailsActivity.statusCode;
//import static com.prakruthi.ui.ui.profile.order_qty.OrderQtyActivity.updateData;
//import static com.prakruthi.ui.ui.profile.order_qty.OrderQtyActivity.JSON_STRING;


import com.prakruthi.ui.ui.profile.order_qty.SaveOrderPurchaseQuantityQtyDataModal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiController {

    //HttpURLConnection:--
//    private static final String BASE_URL = "https://houseofspiritshyd.in/prakruthi/admin/api/";
    private static final String BASE_URL = "https://prakruthiepl.com/admin/api/";
//    private static final String API_URL = "https://prakruthiepl.com/admin/api/saveOrderData";

    public static Boolean statusCode;
    public static String message;

//    String type = "";
//    String color = "";
    public static String productId = "";
    public static String quntity = "";

    //    public ApiResponse saveOrderData(int userId, String token, String updateData) {
//    public SaveOrderPurchaseQuantityQtyDataModal saveOrderData(int userId, String token, String updateData) {
    public SaveOrderPurchaseQuantityQtyDataModal saveOrderData(int userId, String token, String productId, String quntity) {

        SaveOrderPurchaseQuantityQtyDataModal apiResponse = null;

        try {
            URL url = new URL(BASE_URL + "saveOrderData");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            //-----
            JSONObject requestData = new JSONObject();

            JSONObject updateDatas = new JSONObject();
//            JSONObject updateData = new JSONObject();

            //------


            requestData.put("user_id", userId);
            requestData.put("token", token);
//            requestData.put("update_data", updateData);
//            requestData.put("update_data", updateDatas.toString());
//            requestData.put("update_data", updateDatas.toString());
            requestData.put("update_data", productId.toString()+quntity.toString());


            try {
//                updateDatas.put("type",type);
//                updateDatas.put("color",color);
//                updateDatas.put("product_id", productId);
//                updateDatas.put("quntity", quntity);
                requestData.put("product_id", productId);
                requestData.put("quntity", quntity);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            OutputStream os = connection.getOutputStream();
            byte[] input = requestData.toString().getBytes("utf-8");
            os.write(input, 0, input.length);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                br.close();

                JSONObject jsonResponse = new JSONObject(response.toString());

//                apiResponse = new ApiResponse();

                //SaveOrderPurchaseQuantityQtyDataModal apiResponse = new SaveOrderPurchaseQuantityQtyDataModal(arg1Value, arg2Value);
//                apiResponse = new SaveOrderPurchaseQuantityQtyDataModal(apiResponse.isStatus_code(), apiResponse.getMessage());
                apiResponse = new SaveOrderPurchaseQuantityQtyDataModal(statusCode, message);

                apiResponse.setStatus_code(jsonResponse.getBoolean("status_code"));
                apiResponse.setMessage(jsonResponse.getString("message"));
            } else {
                // Handle error cases
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponse;
    }

    //Retrofit:--
//        private ApiService apiService;

//    public ApiController() {
//        // Initialize your ApiService here (using Retrofit)
//        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//    }
//
//    public void saveOrderData(int userId, String token, String updateData, final ApiCallback callback) {
////        Call<ApiResponse> call = apiService.saveOrderData(userId, token, updateData);
//        Call<SaveOrderPurchaseQuantityQtyDataModal> call = apiService.saveOrderData(userId, token, updateData);
//
//        call.enqueue(new Callback<SaveOrderPurchaseQuantityQtyDataModal>() {
//            @Override
//            public void onResponse(Call<SaveOrderPurchaseQuantityQtyDataModal> call, Response<SaveOrderPurchaseQuantityQtyDataModal> response) {
//                if (response.isSuccessful()) {
//                    SaveOrderPurchaseQuantityQtyDataModal apiResponse = response.body();
//                    if (apiResponse != null && apiResponse.isStatus_code()) {
//                        callback.onSuccess(apiResponse.getMessage());
//                    } else {
//                        callback.onError("API response not successful");
//                    }
//                } else {
//                    callback.onError("API response not successful");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SaveOrderPurchaseQuantityQtyDataModal> call, Throwable t) {
//                callback.onError("API call failed");
//            }
//        });
//    }


}

