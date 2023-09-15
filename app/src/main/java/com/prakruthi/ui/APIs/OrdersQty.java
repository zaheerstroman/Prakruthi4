package com.prakruthi.ui.APIs;

import static androidx.fragment.app.FragmentManager.TAG;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.profile.order_qty.OrdersQtyModal;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OrdersQty {

    private OrdersQty.OnProfileOrderQtyApiHitFetchedListener mListener;

    public OrdersQty(OrdersQty.OnProfileOrderQtyApiHitFetchedListener listener) {
        mListener = listener;
    }

    public void HitAPi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new OrdersQty.GetOrdersQtyData());
    }

    private class GetOrdersQtyData implements Runnable {

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
            PutData putData = new PutData(Variables.BaseUrl + "getPurchaseData", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    Log.e(TAG, result );
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        ArrayList<OrdersQtyModal> ordersQtyModal = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject ordersQtyModalData = jsonArray.getJSONObject(i);
                            String id = ordersQtyModalData.getString("id");
                            String product_id = ordersQtyModalData.getString("product_id");
                            String user_id = ordersQtyModalData.getString("user_id");
                            String quantity = ordersQtyModalData.getString("quntity");
                            String used_quntity = ordersQtyModalData.getString("used_quntity");
                            String name = ordersQtyModalData.getString("name");
                            String attachment = ordersQtyModalData.getString("attachment");
                            String remaining_quntity = ordersQtyModalData.getString("remaining_quntity");
                            ordersQtyModal.add(new OrdersQtyModal(id,product_id,user_id,quantity,used_quntity,name,attachment,remaining_quntity));
                        }
                        handleResponse(ordersQtyModal);
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
        mListener.OnProfileItemOrdersQtyAPiGivesError(error);

    }

    private void handleResponse(ArrayList<OrdersQtyModal> ordersQtyModals) {
        mListener.OnProfileItemOrdersQty(ordersQtyModals);

    }


    public interface OnProfileOrderQtyApiHitFetchedListener {

        void OnProfileItemOrdersQty(ArrayList<OrdersQtyModal> ordersQtyModal);

        void OnProfileItemOrdersQtyAPiGivesError(String error);


    }


}
