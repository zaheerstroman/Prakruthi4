package com.prakruthi.ui.APIs;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class GetDeliveryAddressDetails extends AsyncTask<Void, Void, ArrayList<Address_BottomSheet_Recycler_Adaptor_Model>> {
    private static final String URL = Variables.BaseUrl+"getDeliveryAddressDetails";
    private static final String[] FIELD = {"user_id", "token"};
    private final String[] DATA = {String.valueOf(Variables.id), Variables.token};

    private DeliveryAddressListener mListener;

    public GetDeliveryAddressDetails(DeliveryAddressListener listener) {
        mListener = listener;
    }

    @Override
    protected ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> doInBackground(Void... voids) {
        ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList = new ArrayList<>();

        // Create PutData object to make API call
        PutData putData = new PutData(URL, "POST", FIELD, DATA);

        // Start API call
        if (putData.startPut()) {
            if (putData.onComplete()) {
                String response = putData.getResult();
                try {
                    // Parse JSON response
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("delivery_address");

                    // Loop through JSON array and create Address objects
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String name = obj.getString("name");
                        String address = obj.getString("full_address");

                        String state = obj.getString("state");
                        String city = obj.getString("city");

                        int Defualt = obj.getInt("is_default");
                        int id = obj.getInt("id");
                        Variables.addressID= String.valueOf(id);
                        if (Defualt == 1)
                        {
                            Variables.address = address;
                        }
                        addressList.add(new Address_BottomSheet_Recycler_Adaptor_Model(name, address,state,city,Defualt,id));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return addressList;
    }

    @Override
    protected void onPostExecute(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList) {
        super.onPostExecute(addressList);
        mListener.onDeliveryAddressLoaded(addressList);
    }

    public interface DeliveryAddressListener {
        void onDeliveryAddressLoaded(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList);
    }
}