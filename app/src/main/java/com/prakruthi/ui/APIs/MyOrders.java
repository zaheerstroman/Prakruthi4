package com.prakruthi.ui.APIs;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.profile.myorders.MyOrdersModal;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.jar.JarException;

public class MyOrders {

    private MyOrders.OnProfileMyOrdersApiHitFetchedListener mListener;

    public MyOrders(MyOrders.OnProfileMyOrdersApiHitFetchedListener listener) {
        mListener = listener;
    }

    public void HitAPi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new MyOrders.GetMyOrdersData());
    }

    private class GetMyOrdersData implements Runnable {

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
            PutData putData = new PutData(Variables.BaseUrl + "myOrders", "POST", field, data);

            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        ArrayList<MyOrdersModal> myOrdersModals = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject myordersModalData = jsonArray.getJSONObject(i);
                            String id = myordersModalData.getString("id");
                            String user_id = myordersModalData.getString("user_id");
                            String product_id = myordersModalData.getString("product_id");
                            String quantity = myordersModalData.getString("quantity");
                            String amount = myordersModalData.getString("amount");
                            String order_id = myordersModalData.getString("order_id");
                            String status = myordersModalData.getString("status");
                            String is_paid = myordersModalData.getString("is_paid");
                            String invoice_id = myordersModalData.getString("invoice_id");
                            String tracking_status = myordersModalData.getString("tracking_status");
                            String tracking_id = myordersModalData.getString("tracking_id");
                            String partner_name = myordersModalData.getString("partner_name");
                            String attachment = myordersModalData.getString("attachment");
                            String name = myordersModalData.getString("name");
                            myOrdersModals.add(new MyOrdersModal(id,user_id,product_id,quantity,amount,order_id,status,is_paid,invoice_id,tracking_status,tracking_id,partner_name,attachment,name));
                        }
                        handleResponse(myOrdersModals);
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                        handleError("Internal Error");
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
        mListener.OnProfileItemMyOrdersAPiGivesError(error);

    }

    private void handleResponse(ArrayList<MyOrdersModal> myOrdersModals) {
        mListener.OnProfileItemMyOrders(myOrdersModals);
    }


    public interface OnProfileMyOrdersApiHitFetchedListener {

        void OnProfileItemMyOrders(ArrayList<MyOrdersModal> myOrdersModals);

        void OnProfileItemMyOrdersAPiGivesError(String error);


    }


}