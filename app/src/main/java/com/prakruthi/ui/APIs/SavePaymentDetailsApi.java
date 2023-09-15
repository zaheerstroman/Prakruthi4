package com.prakruthi.ui.APIs;

//import static com.prakruthi.ui.ui.productPage.SavePaymentDetailsActivity.productIds;

import android.util.Log;
import android.view.View;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.productPage.SavePaymentDetailsModel;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SavePaymentDetailsApi {

    OnSavePaymentDetailsApiHitListner mListner;

public static String address, productIds, totalAmount;

    public SavePaymentDetailsApi(OnSavePaymentDetailsApiHitListner mListner, String address, String productIds, String totalAmount) {
        this.mListner = mListner;
        this.address = address;
        this.productIds = productIds;
        this.totalAmount = totalAmount;

    }

    public void HitSavePaymentDetailsApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new SavePaymentDetailsApiApi());
    }


    private class SavePaymentDetailsApiApi implements Runnable {

        @Override
        public void run() {
            HashMap<Integer, Integer> filtersProductIdArrayOfTwo = new HashMap<>();
            String[] productIds = {"3", "0", "1"}; // Assuming you want to map 3 to 0 and 0 to 1

            for (int i = 0; i < productIds.length; i++) {
                filtersProductIdArrayOfTwo.put(Integer.valueOf(productIds[i]), i);

            }

            // You can also manually add more mappings if needed
            filtersProductIdArrayOfTwo.put(4, 2);
            filtersProductIdArrayOfTwo.put(2, 3);
            filtersProductIdArrayOfTwo.put(1, 4);
            filtersProductIdArrayOfTwo.put(3, 1);

            // Now you can access the positions using the product IDs
            int positionOfProduct3 = filtersProductIdArrayOfTwo.get(3); // This will give you 0
            int positionOfProduct0 = filtersProductIdArrayOfTwo.get(0); // This will give you 1

            System.out.println("Position of product 3: " + positionOfProduct3);
            System.out.println("Position of product 0: " + positionOfProduct0);

//                filtersProductIdArrayOfTwo.put(TotalAmount,TotalAmount);

            //Creating array for parameters
            String[] field = new String[5];
            field[0] = "user_id";
            field[1] = "token";
            field[2] = "address";
            field[3] = "product_ids";
            field[4] = "total_amount";

            //Creating array for data
            String[] data = new String[5];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
            data[2] = address;
            data[3] = filtersProductIdArrayOfTwo.toString();
            data[4] = totalAmount;


            PutData putData = new PutData(Variables.companyBaseUrl + "payment_gateway.php", "POST", field, data);

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
    }

    private void handleResponse(String result) {

        if (result != null) {
            try {
                Log.wtf("TAG", result);

                JSONObject response = new JSONObject(result);

                ArrayList<SavePaymentDetailsModel> savePaymentDetailsModels = new ArrayList<>();


                String paymentUrl = response.optString("payment_url");

                String invoiceId = String.valueOf(response.getInt("invoice_id"));

                String invoiceNum = String.valueOf(response.getInt("invoice_num"));

                boolean statusCode = response.optBoolean("status_code");

                String message = response.optString("message");

                savePaymentDetailsModels.add(new SavePaymentDetailsModel(paymentUrl, invoiceId, invoiceNum,statusCode, message));

                SavePaymentDetailsModel.setPaymentUrl("payment_url");

                SavePaymentDetailsModel.setInvoiceId(String.valueOf("invoice_id"));

                SavePaymentDetailsModel.setInvoiceNum(String.valueOf("invoice_num"));

                SavePaymentDetailsModel.setStatusCode(Boolean.valueOf("status_code"));

                SavePaymentDetailsModel.setMessage(response.getString("message"));



                if (response.getBoolean("status_code")) {
//                    mListener.onUserDetailsUpdate(jsonResponse.getString("message"));
                    mListner.OnSavePaymentDtailsResult(response.getString("message"));
                } else {
                    handleError(response.getString("message"));
                }

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
