package com.prakruthi.ui.APIs;

import static com.prakruthi.ui.Variables.rating;
import static com.prakruthi.ui.ui.AddReviewsUserDetails.review;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.AddReviewsUserDetails;
import com.prakruthi.ui.ui.productPage.productReviews.ProductReviewsModel;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GetProductReviews {
    private OnGetProductReviewsHits mListner;
    private String ProductID;


    public void HitReviewsApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new HitAPi());
    }

    public GetProductReviews(String ProductId,OnGetProductReviewsHits mListner)
    {
        this.ProductID = ProductId;
        this.mListner = mListner;
    }


    private class HitAPi implements Runnable {
        @Override
        public void run() {
            String[] field = new String[3];
            field[0] = "product_id";
            field[1] = "user_id";
            field[2] = "token";
            //Creating array for data
            String[] data = new String[3];
            data[0] = ProductID;
            data[1] = String.valueOf(Variables.id);
            data[2] = Variables.token;
            PutData putData = new PutData(Variables.BaseUrl + "getReviews", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    //End ProgressBar (Set visibility to GONE)
                    Log.i("PutData", result);


                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        ArrayList<ProductReviewsModel> productReviewsModels = new ArrayList<>();
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject Resultdata = jsonArray.getJSONObject(i);

                            String review = Resultdata.getString("review");
                            String rating = Resultdata.getString("rating");

                            productReviewsModels.add(new ProductReviewsModel(rating, review));

                        }
                        mListner.OnGetProductReviewsResult(productReviewsModels);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        try {
                            mListner.OnGetProductReviewsError(new JSONObject(result).getString("message"));
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                            mListner.OnGetProductReviewsError("Internal Error No Message Found");
                        }
                    }
                }
            }
        }
    }

    public interface OnGetProductReviewsHits {
        void OnGetProductReviewsResult(ArrayList<ProductReviewsModel> productReviewsModels);

        void OnGetProductReviewsError(String error);
    }
}
