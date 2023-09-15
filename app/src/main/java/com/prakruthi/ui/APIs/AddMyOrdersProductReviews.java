package com.prakruthi.ui.APIs;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.productPage.ProductPage;
import com.prakruthi.ui.ui.productPage.productReviews.AddProductReviewsModel;
import com.prakruthi.ui.ui.productPage.productReviews.ProductReviewsModel;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddMyOrdersProductReviews {
    private OnAddMyOrdersProductReviewsHitsListener mListner;
    private String ProductID, rating, review;

    public void HitAddMyOrdersProductReviewsApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new HitAddMyOrdersProductReviewsApis());
    }


    public AddMyOrdersProductReviews(String ProductId,String rating, String review,OnAddMyOrdersProductReviewsHitsListener mListner)
    {
        this.ProductID = ProductId;
        this.rating = rating;
        this.review = review;
        this.mListner = mListner;

    }


    private class HitAddMyOrdersProductReviewsApis implements Runnable {
        @Override
        public void run() {
            String[] field = new String[5];
            field[0] = "product_id";
            field[1] = "user_id";
            field[2] = "token";
            field[3] = "rating";
            field[4] = "review";

            String[] data = new String[5];
            data[0] = ProductID;
            data[1] = String.valueOf(Variables.id);
            data[2] = Variables.token;
            data[3] = rating;
            data[4] = review;

            PutData putData = new PutData(Variables.BaseUrl + "addReview", "POST", field, data);

            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();

                    Log.i("PutData", result);

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getBoolean("status_code")) {
                            mListner.OnAddProductReviewsResult();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mListner.OnGetProductReviewsError(e.getLocalizedMessage());
                    }



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
                JSONObject jsonResponse = new JSONObject(result);
                if (jsonResponse.getBoolean("status_code")) {

                    mListner.OnAddProductReviewsResult();

                }


            } catch (JSONException e) {
                e.printStackTrace();

                mListner.OnGetProductReviewsError(e.getLocalizedMessage());

            }
        } else {
            handleError("Failed to fetch data");
        }

    }

    private void handleError(String failed_to_fetch_data) {
        mListner.OnGetProductReviewsError(failed_to_fetch_data);

    }

    public interface OnAddMyOrdersProductReviewsHitsListener {

        void OnAddProductReviewsResult();

        void OnGetProductReviewsError(String error);
    }


}
