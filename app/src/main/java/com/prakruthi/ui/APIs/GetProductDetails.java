package com.prakruthi.ui.APIs;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.productPage.ProductModel;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GetProductDetails {
    private OnProductDataFetched mListener;
    private static String productId;

    public GetProductDetails(OnProductDataFetched listner , String productId)
    {
        this.mListener = listner;
        this.productId = productId;
    }
    public void fetchData() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new GetProductData());
    }

    private class GetProductData implements Runnable
    {

        @Override
        public void run() {
            //Creating array for parameters
            String[] field = new String[3];
            field[0] = "user_id";
            field[1] = "token";
            field[2] = "product_id";
            //Creating array for data
            String[] data = new String[3];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
            data[2] = productId;
            PutData putData = new PutData(Variables.BaseUrl+"getProductDetails", "POST", field, data);
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
            int statusCode = response.optBoolean("status_code") ? 1 : 0;
            String message = response.optString("message");

            JSONArray productDetailsArray = response.getJSONArray("products_details");

            if (statusCode == 1 && productDetailsArray.length() > 0) {
                JSONObject productDetails = productDetailsArray.getJSONObject(0);

                // Rest of the code remains the same
                ProductModel product = new ProductModel();
                product.setId(productDetails.getInt("id"));
                product.setCategoryId(productDetails.getInt("category_id"));
                product.setSubcategoryId(productDetails.getInt("subcategory_id"));
                product.setName(productDetails.getString("name"));
                product.setDescription(productDetails.getString("description"));

                List<String> attachments = new ArrayList<>();
                for (int i = 1; i <= 10; i++) {
                        String attachment = productDetails.getString("attachment" + i);
                        if (!attachment.equals("null")) {
                            attachments.add(attachment);
                        }
                }
                product.setAttachments(attachments);

                product.setActualPrice(productDetails.getString("actual_price"));
                product.setCustomerPrice(productDetails.getString("customer_price"));
                product.setDealerPrice(productDetails.getString("delar_price"));
                product.setMartPrice(productDetails.getString("mart_price"));

                if (productDetails.isNull("is_trending")) {
                    product.setIsTrending(0);
                } else {
                    product.setIsTrending(productDetails.getInt("is_trending"));
                }

                product.setYoutubeLink(productDetails.optString("youtube_link"));
                product.setColor(productDetails.getString("color"));
                product.setSize(productDetails.getString("size"));
                product.setType(productDetails.getString("type"));
                product.setIn_wishlist(productDetails.getBoolean("in_wishlist"));
                product.setRating(productDetails.getString("rating"));
                product.setCount_rating(productDetails.getString("count_rating"));

                product.setIs_review(productDetails.getBoolean("is_review"));


                // Use the product and status code as needed
                mListener.OnDataFetched(product);
            } else {
                // Handle the case when no product details are available or the status code is not 1
                mListener.OnDataFetchError(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON parsing error
            mListener.OnDataFetchError("JSON parsing error");
        }
    }

    private void handleError(String failed_to_fetch_data) {

    }
    public interface OnProductDataFetched
    {
        void OnDataFetched(ProductModel productModel);

        void OnDataFetchError(String message);
    }
}
