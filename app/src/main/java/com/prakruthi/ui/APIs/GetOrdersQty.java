package com.prakruthi.ui.APIs;

import android.os.AsyncTask;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.profile.order_qty.OrdersQtyModal;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetOrdersQty extends AsyncTask<Void, Void, ArrayList<OrdersQtyModal>> {

    private static final String URL = Variables.BaseUrl + "getPurchaseData";

    private static final String[] FIELD = {"user_id", "token"};

    private final String[] DATA = {String.valueOf(Variables.id), Variables.token};

    private OnProfileOrderQtyApiHitFetchedListener mListener;

    public GetOrdersQty(OnProfileOrderQtyApiHitFetchedListener listener) {
        mListener = listener;
    }

    @Override
    protected ArrayList<OrdersQtyModal> doInBackground(Void... voids) {
        ArrayList<OrdersQtyModal> addressList = new ArrayList<>();

        PutData putData = new PutData(URL, "POST", FIELD, DATA);


        // Start API call
        if (putData.startPut()) {
            if (putData.onComplete()) {
                String response = putData.getResult();
                try {
                    // Parse JSON response
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    // Loop through JSON array and create Address objects
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String id = obj.getString("id");
                        String product_id = obj.getString("product_id");
                        String user_id = obj.getString("user_id");
                        String quantity = obj.getString("quntity");
                        String used_quntity = obj.getString("used_quntity");
                        String name = obj.getString("name");
                        String attachment = obj.getString("attachment");
                        String remaining_quntity = obj.getString("remaining_quntity");
//                        int Defualt = obj.getInt("is_default");
//                        int id = obj.getInt("id");
//                        if (Defualt == 1)
//                        {
//                            Variables.address = address;
//                        }
                        addressList.add(new OrdersQtyModal(id, product_id, user_id, quantity, used_quntity, name, attachment, remaining_quntity));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return addressList;
    }

    @Override
    protected void onPostExecute(ArrayList<OrdersQtyModal> addressList) {
        super.onPostExecute(addressList);
//        mListener.onDeliveryAddressLoaded(addressList);
        mListener.OnProfileItemOrdersQty(addressList);
    }


    public interface OnProfileOrderQtyApiHitFetchedListener {

        void OnProfileItemOrdersQty(ArrayList<OrdersQtyModal> addressList);

//        void OnProfileItemOrdersQtyAPiGivesError(String error);


    }
}


