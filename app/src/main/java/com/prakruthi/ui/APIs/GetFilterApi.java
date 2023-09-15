package com.prakruthi.ui.APIs;

import static com.google.firebase.messaging.Constants.TAG;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GetFilterApi {

    private OnFilterAPiHit mListner;


    public GetFilterApi(OnFilterAPiHit mListner)
    {
        this.mListner = mListner;
    }

    public void HitApi()
    {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new FilterApi());
    }

    public class FilterApi implements Runnable
    {
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
            PutData putData = new PutData(Variables.BaseUrl+"filterData", "POST", field, data);

            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    Log.e(TAG, result );
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        mListner.OnFilterApiSuccess(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mListner.OnFilterApiFailed("Internal Error");
                    }
                } else {
                    mListner.OnFilterApiFailed("Failed to fetch data");
                }
            } else {
                mListner.OnFilterApiFailed("Failed to connect to server");
            }
        }
    }

    public interface OnFilterAPiHit
    {
        void OnFilterApiSuccess(JSONObject result);
        void OnFilterApiFailed(String result);
    }
}
