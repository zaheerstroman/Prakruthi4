package com.prakruthi.ui.APIs;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.profile.recentProducts.RecentProductModel;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddRecentViewProductsAPI {
    private String ProductID;


    public void HitRecentApi()
    {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new HitAPi());
    }

    public AddRecentViewProductsAPI(String ProductId)
    {
        this.ProductID = ProductId;
    }
    private class HitAPi implements Runnable
    {
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
            PutData putData = new PutData(Variables.BaseUrl+"addRecentViewData", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    Log.i("PutData", result);
                }
            }
        }
    }
}
