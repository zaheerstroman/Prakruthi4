package com.prakruthi.ui.APIs;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.home.products.HomeProductModel;
import com.prakruthi.ui.ui.profile.recentProducts.RecentProductModel;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.jar.JarException;

public class GetRecentViewProductsAPI {

    private OnGetRecentViewProductsAPIHit mListner;

    public GetRecentViewProductsAPI(OnGetRecentViewProductsAPIHit mListner)
    {
        this.mListner = mListner;
    }

    public void HitRecentApi()
    {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new HitAPi());
    }

    private class HitAPi implements Runnable
    {
        @Override
        public void run() {
            String[] field = new String[2];
            field[0] = "user_id";
            field[1] = "token";
            //Creating array for data
            String[] data = new String[2];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
            PutData putData = new PutData(Variables.BaseUrl+"getRecentViewData", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    //End ProgressBar (Set visibility to GONE)
                    Log.i("PutData", result);
                    try {
                        JSONObject jsonResponse = new JSONObject(result);
                        JSONArray productList = jsonResponse.getJSONArray("data");
                        if (productList.length()!=0) {
                            ArrayList<RecentProductModel> recentProductModels = new ArrayList<>();
                            for (int i = 0; i < productList.length(); i++) {
                                JSONObject product = productList.getJSONObject(i);
                                String id = product.getString("id");
                                String user_id = product.getString("user_id");
                                String product_id = product.getString("product_id");
                                String logged_date = product.getString("logged_date");
                                String created_by = product.getString("created_by");
                                String updated_by = product.getString("updated_by");
                                String updated_date = product.getString("updated_date");
                                String name = product.getString("name");
                                String attachment = product.getString("attachment");
                                String actual_price = product.getString("actual_price");
                                String customer_price = product.getString("customer_price");
                                String delar_price = product.getString("delar_price");
                                String mart_price = product.getString("mart_price");
                                String rating = product.getString("rating");
                                String count_rating = product.getString("count_rating");
                                recentProductModels.add(new RecentProductModel(id, user_id, product_id, logged_date, created_by, updated_by, updated_date, name, attachment, actual_price, customer_price, delar_price, mart_price, rating, count_rating));
                            }

                            mListner.OnGetRecentViewProductsAPIGivesResult(recentProductModels);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            mListner.OnGetRecentViewProductsAPIGivesError(jsonObject.getString("message"));
                        }
                        catch (Exception e1)
                        {
                            e1.printStackTrace();
                            mListner.OnGetRecentViewProductsAPIGivesError("Internal Error");
                        }
                    }

                }
            }
        }
    }


    public interface OnGetRecentViewProductsAPIHit
    {
        void OnGetRecentViewProductsAPIGivesResult(ArrayList<RecentProductModel> recentProductModels);
        void OnGetRecentViewProductsAPIGivesError(String error);
    }
}
