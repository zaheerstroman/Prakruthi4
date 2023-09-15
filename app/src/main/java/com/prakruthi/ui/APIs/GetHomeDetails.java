package com.prakruthi.ui.APIs;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.home.banners.HomeBannerModel;
import com.prakruthi.ui.ui.home.category.HomeCategoryModal;
import com.prakruthi.ui.ui.home.products.HomeProductModel;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GetHomeDetails {
    private final OnDataFetchedListener mListener;

    public GetHomeDetails(OnDataFetchedListener listener) {
        mListener = listener;
    }

    public void fetchData() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new GetDataTask());
    }

    private class GetDataTask implements Runnable {

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
            PutData putData = new PutData(Variables.BaseUrl+"getDashboardDetails", "POST", field, data);
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

        private void handleResponse(String result) {
            if (result != null) {
                try {
                    Log.wtf("TAG", result );
                    JSONObject jsonResponse = new JSONObject(result);
                    JSONArray bannerList = jsonResponse.getJSONObject("data").getJSONArray("banner_list");
                    JSONArray categoryList = jsonResponse.getJSONObject("data").getJSONArray("category_list");
                    JSONArray productList = jsonResponse.getJSONObject("data").getJSONArray("products_list");

                    // Create lists
                    List<HomeCategoryModal> homeCategoryModals = new ArrayList<>();
                    List<HomeBannerModel> homeBannerModels = new ArrayList<>();
                    List<HomeProductModel> homeProductModels = new ArrayList<>();

                    // Populate lists
                    for (int i = 0; i < categoryList.length(); i++) {
                        JSONObject category = categoryList.getJSONObject(i);
                        int id = category.getInt("id");
                        String name = category.getString("name");
                        String attachment = category.getString("attachment");
                        homeCategoryModals.add(new HomeCategoryModal(id, name, attachment));
                    }

                    for (int i = 0; i < bannerList.length(); i++) {
                        JSONObject banner = bannerList.getJSONObject(i);
                        int id = banner.getInt("id");
                        String attachment = banner.getString("attachment");
                        homeBannerModels.add(new HomeBannerModel(id, attachment));
                    }

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

                    // Call listener with all three lists
                    mListener.onCategoryFetched(homeCategoryModals);
                    mListener.onBannerListFetched(homeBannerModels);
                    mListener.onProductListFetched(homeProductModels);
                } catch (JSONException e) {
                    e.printStackTrace();
                    handleError("Failed to parse data");
                }
            } else {
                handleError("Failed to fetch data");
            }
        }


        private void handleError(String error) {
            mListener.onDataFetchError(error);
        }
    }

    public interface OnDataFetchedListener {
        void onCategoryFetched(List<HomeCategoryModal> homeCategoryModals);
        void onBannerListFetched(List<HomeBannerModel> homeBannerModels);
        void onProductListFetched(List<HomeProductModel> homeProductModels);
        void onDataFetchError(String error);
    }


}
