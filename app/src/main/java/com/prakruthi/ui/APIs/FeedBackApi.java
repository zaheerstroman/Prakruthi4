package com.prakruthi.ui.APIs;

import static androidx.fragment.app.FragmentManager.TAG;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.prakruthi.ui.Variables;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FeedBackApi extends AppCompatActivity {

    public static Dialog HitFeedbackAPi;
    Context context;

    private FeedBackApi.OnFeedbackItemAPiHit mListner;
    private String Description;


    public FeedBackApi(FeedBackApi.OnFeedbackItemAPiHit listner , String Description) {
        mListner = listner;
        this.Description = Description;
    }

    public void FeedbackHitApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new FeedBackApi.HitFeedbackAPi());
    }

    public class HitFeedbackAPi implements Runnable{

        @Override
        public void run() {

            //Creating array for parameters
            String[] field = new String[3];
            field[0] = "user_id";
            field[1] = "token";
            field[2] = "description";

            //Creating array for data
            String[] data = new String[3];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
            data[2] = Description;

            PutData putData = new PutData(Variables.BaseUrl + "feedback", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();

                    try {
                        JSONObject response = new JSONObject(result);

                        boolean statusCode = response.getBoolean("status_code");

                        if (statusCode)
                        {
                            handleResponse("Thanks For Your Feedback");
                        }
                        else
                        {
                            handleResponse("Internal Error");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    handleError("Failed to fetch data");
                }
            } else {
                handleError("Failed to connect to server");
            }

        }
    }

    private void handleError(String error) {
        mListner.OnProfileItemFeedbackAPiGivesError(error);
    }

    private void handleResponse(String description) {
        mListner.OnProfileItemFeedback(description);

    }


    public interface OnFeedbackItemAPiHit {
        void OnProfileItemFeedback(String description);

        void OnProfileItemFeedbackAPiGivesError(String error);
    }


}