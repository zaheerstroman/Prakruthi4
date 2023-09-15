package com.prakruthi.ui.APIs;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.home.products.HomeProductModel;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GetProductsList {
    private OnCategoryProductsFetchedListner mListner;
    private String categoryid;

    public GetProductsList(OnCategoryProductsFetchedListner listner,String categoryid)
    {
        mListner = listner;
        this.categoryid = categoryid;
    }

    public void HitGetProductListAPi()
    {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new GetProductsListApi());
    }

    private class GetProductsListApi implements Runnable
    {

        @Override
        public void run() {
            //Creating array for parameters
            String[] field = new String[3];
            field[0] = "user_id";
            field[1] = "token";
            field[2] = "category_id";
            //Creating array for data
            String[] data = new String[3];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
            data[2] = categoryid;
            PutData putData = new PutData(Variables.BaseUrl+"getProductsList", "POST", field, data);
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

    private void handleError(String failed_to_fetch_data) {
        mListner.OnGetProductsListApiGivesError(failed_to_fetch_data);
    }

    private void handleResponse(String result) {
        try {
            JSONObject jsonResponse = new JSONObject(result);
            JSONArray productList = jsonResponse.getJSONArray("products_details");
            List<HomeProductModel> homeProductModels = new ArrayList<>();
            for (int i = 0; i < productList.length(); i++) {
                JSONObject productlist = productList.getJSONObject(i);
                int id = productlist.getInt("id");
                String categoryId = productlist.getString("category_id");
                String name = productlist.getString("name");
                String attachment = productlist.getString("attachment");
                String description = productlist.getString("description");
                String actual_price = productlist.getString("actual_price");
                String customer_price = productlist.getString("customer_price");
                String delar_price = productlist.getString("delar_price");
                String mart_price = productlist.getString("mart_price");
                String rating = productlist.getString("rating");
                String count_rating = productlist.getString("count_rating");
                homeProductModels.add(new HomeProductModel(id, name, attachment , rating , count_rating , actual_price , customer_price , delar_price , mart_price , description));
            }
            mListner.OnCategoryProductsFetched(homeProductModels);
        }
        catch (JSONException e) {
            e.printStackTrace();
            try {
                JSONObject jsonObject = new JSONObject(result);
                handleError(jsonObject.getString("message"));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }


    }

    public interface OnCategoryProductsFetchedListner
    {
        void OnCategoryProductsFetched(List<HomeProductModel> homeProductModel);
        void OnGetProductsListApiGivesError(String error);
    }
}
