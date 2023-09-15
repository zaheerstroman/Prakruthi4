package com.prakruthi.ui.APIs;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.prakruthi.ui.Variables;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class DefaultDeliveryAddressDetails extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        //Creating array for parameters
        String[] field = new String[4];
        field[0] = "id";
        field[1] = "user_id";
        field[2] = "token";
        field[3] = "is_default";

        //Creating array for data
        String[] data = new String[4];
        data[0] = params[0];
        data[1] = String.valueOf(Variables.id);
        data[2] = Variables.token;
        data[3] = "1";

        //Creating PutData object and executing the request
        PutData putData = new PutData(Variables.BaseUrl+"saveDeliveryAddressDetails", "POST", field, data);
        if (putData.startPut()) {
            if (putData.onComplete()) {
                return putData.getResult();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            Log.i("PutData", result);
        } else {
            Log.e("PutData", "Error: request failed.");
        }
    }

}
