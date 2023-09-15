package com.prakruthi.ui.APIs;

import static androidx.fragment.app.FragmentManager.TAG;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.productPage.ProductModel;
import com.prakruthi.ui.ui.search.SearchModle;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SearchProductApi {

    OnSearchResultApiHit mListner;
    String ProductName;
    String order = "";
    String type = "";
    String color = "";
    public SearchProductApi(OnSearchResultApiHit listner , String ProductName , String order, String type , String color)
    {
        mListner = listner;
        this.ProductName = ProductName;
        this.order = order;
        this.type = type;
        this.color = color;
    }

    public void HitSearchApi()
    {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new SearchApi());
    }

    private class SearchApi implements Runnable
    {

        @Override
        public void run() {
            JSONObject filters = new JSONObject();
            try {
                filters.put("type",type);
                filters.put("color",color);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Creating array for parameters
            String[] field = new String[5];
            field[0] = "user_id";
            field[1] = "token";
            field[2] = "product_name";
            field[3] = "order";
            field[4] = "filter";
            //Creating array for data
            String[] data = new String[5];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
            data[2] = ProductName;
            data[3] = order;
            data[4] = filters.toString();

            PutData putData = new PutData(Variables.BaseUrl+"getSearchProductsList", "POST", field, data);
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
        try {
            JSONObject response = new JSONObject(result);
            boolean statusCode = response.optBoolean("status_code");
            String message = response.optString("message");


            if (statusCode) {
                JSONArray productDetailsArray = response.getJSONArray("products_details");
                List<SearchModle> searchResults = new ArrayList<>();
                for (int i = 0; i < productDetailsArray.length(); i++) {
                    JSONObject productDetails = productDetailsArray.getJSONObject(i);
                    // Rest of the code remains the same
                    SearchModle searchModle = new SearchModle();
                    searchModle.setId(productDetails.getInt("id"));
                    searchModle.setCategoryId(productDetails.getInt("category_id"));
                    searchModle.setSubcategoryId(productDetails.getInt("subcategory_id"));
                    searchModle.setName(productDetails.getString("name"));
                    searchModle.setDescription(productDetails.getString("description"));

                    List<String> attachments = new ArrayList<>();
                    for (int j = 1; j <= 10; j++) {
                        String attachment = productDetails.getString("attachment" + j);
                        if (attachment != null && !attachment.equals("null")) {
                            attachments.add(attachment);
                        }
                    }
                    searchModle.setAttachments(attachments);

                    searchModle.setActualPrice(productDetails.getString("actual_price"));
                    searchModle.setCustomerPrice(productDetails.getString("customer_price"));
                    searchModle.setDealerPrice(productDetails.getString("delar_price"));
                    searchModle.setMartPrice(productDetails.getString("mart_price"));
                    searchModle.setIsTrending(productDetails.isNull("is_trending") ? 0 : productDetails.getInt("is_trending"));
                    searchModle.setYoutubeLink(productDetails.optString("youtube_link"));
                    searchModle.setColor(productDetails.getString("color"));
                    searchModle.setSize(productDetails.getString("size"));
                    searchModle.setType(productDetails.getString("type"));
                    searchModle.setRating(productDetails.getString("rating"));
                    searchModle.setCount_rating(productDetails.getString("count_rating"));
                    searchResults.add(searchModle);
                }

                // Use the product and status code as needed
                mListner.OnSearchResult(searchResults);
            }else {
                // Handle the case when no product details are available or the status code is not true
                mListner.OnSearchResultApiGivesError(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON parsing error
            mListner.OnSearchResultApiGivesError("JSON parsing error");
        }

    }

    private void handleError(String failed_to_fetch_data) {
        mListner.OnSearchResultApiGivesError(failed_to_fetch_data);
    }

    public interface OnSearchResultApiHit
    {
        void OnSearchResult(List<SearchModle> product);
        void OnSearchResultApiGivesError(String error);
    }
}
