package com.prakruthi.ui.ui.productPage;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SavePaymentDetailsApi {

    OnSavePaymentDetailsApiHitListner mListner;


    String TotalAmount;

    String ProductIds;



    public SavePaymentDetailsApi(OnSavePaymentDetailsApiHitListner mListner, String totalAmount, String productIds) {
        this.mListner = mListner;
        TotalAmount = totalAmount;
        ProductIds = productIds;
    }

    public void HitSavePaymentDetailsApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new SavePaymentDetailsApiApi());
    }

    private class SavePaymentDetailsApiApi implements Runnable {

        @Override
        public void run() {
            JSONObject filtersProductIdArrayOfTwo = new JSONObject();
            try {
//                filters.put("type",type);
//                filters.put("color",color);
//                filters.put("ProductIds",ProductIds);
//                filters.put("TotalAmount",TotalAmount);
                filtersProductIdArrayOfTwo.put(ProductIds, ProductIds);
                filtersProductIdArrayOfTwo.put("4", 3);
                filtersProductIdArrayOfTwo.put("3", 2);
                filtersProductIdArrayOfTwo.put("2", 1);
                filtersProductIdArrayOfTwo.put("1", 0);

//                filtersProductIdArrayOfTwo.put(TotalAmount,TotalAmount);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Creating array for parameters
            String[] field = new String[4];
            field[0] = "user_id";
            field[1] = "token";
//          field[2] = "product_name";
            field[2] = "product_ids";
            field[3] = "total_amount";
//            field[4] = "filter";

            //Creating array for data
            String[] data = new String[4];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;

//            data[2] = ProductName;
//            data[2] = ProductIds;
            data[2] = filtersProductIdArrayOfTwo.toString();

//            data[3] = order;
            data[3] = TotalAmount;
//            data[4] = filters.toString();


            PutData putData = new PutData(Variables.BaseUrl + "savePaymentDetails", "POST", field, data);

            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();

                    try {
                        JSONObject response = new JSONObject(result);

                        //RESPONSE
                        boolean statusCode = response.getBoolean("status_code");

                        if (statusCode) {
                            handleResponse("Thanks For Your Status");
                        } else {
                            handleResponse("Internal Error");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    handleResponse(result);
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

                Log.wtf("TAG", result );

                JSONObject response = new JSONObject(result);

                ArrayList<SavePaymentDetailsModel> savePaymentDetailsModels = new ArrayList<>();


                String paymentUrl = response.optString("payment_url");

                String invoiceId = String.valueOf(response.getInt("invoice_id"));
                String invoiceNum = String.valueOf(response.getInt("invoice_num"));

                boolean statusCode = response.optBoolean("status_code");
                String message = response.optString("message");


                    SavePaymentDetailsModel.setPaymentUrl(String.valueOf(response.getInt("payment_url")));
//                    SavePaymentDetailsModel.setInvoiceId(response.getInt("invoice_id"));
                SavePaymentDetailsModel.setInvoiceId(String.valueOf(response.getInt("invoice_id")));

                SavePaymentDetailsModel.setStatusCode(Boolean.valueOf(response.getString("status_code")));
                    SavePaymentDetailsModel.setMessage(response.getString("message"));

                    savePaymentDetailsModels.add(new SavePaymentDetailsModel(paymentUrl,invoiceId,invoiceNum,statusCode, message));


                //----------------------------------------

//            AddMyOrdersProductReviews

                if (response.getBoolean("status_code")) {
//                    mListener.onUserDetailsUpdate(jsonResponse.getString("message"));
                    mListner.OnSavePaymentDtailsResult(response.getString("message"));
                } else {
                    handleError(response.getString("message"));
                }

                //-------------------------------


            } catch (JSONException e) {
                e.printStackTrace();
                // Handle JSON parsing error
                mListner.OnOnSavePaymentDtailsResultApiGivesError("JSON parsing error");


            }
        }

    }

    private void handleError(String failed_to_fetch_data) {
        mListner.OnOnSavePaymentDtailsResultApiGivesError(failed_to_fetch_data);
    }


    public interface OnSavePaymentDetailsApiHitListner {
        void OnSavePaymentDtailsResult(String product);

        void OnOnSavePaymentDtailsResultApiGivesError(String error);
    }

}
