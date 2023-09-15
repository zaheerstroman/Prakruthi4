package com.prakruthi.ui.APIs;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.profile.myorders.MyOrdersModal;
import com.prakruthi.ui.ui.profile.mypayments.MyPaymentsInvoiceProductModel;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InvoiceProductsApi {

     String invoice_id;
    private InvoiceProductsApi.OnProfileMyPaymentsInvoiceProductApiHitFetchedListener mListener;

    public InvoiceProductsApi(InvoiceProductsApi.OnProfileMyPaymentsInvoiceProductApiHitFetchedListener listener, String invoice_id) {
        mListener = listener;
        this.invoice_id = invoice_id;
    }

    public void HitProfileMyPaymentsInvoiceProductAPi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new InvoiceProductsApi.GetMyPaymentsInvoiceProductsData());
    }

    private class GetMyPaymentsInvoiceProductsData implements Runnable {

        @Override
        public void run() {
            //Creating array for parameters
            String[] field = new String[3];
            field[0] = "user_id";
            field[1] = "token";
            field[2] = "invoice_id";
            //Creating array for data
            String[] data = new String[3];
            data[0] = String.valueOf(Variables.id);
//            data[0] = String.valueOf(Variables.userId);
            data[1] = Variables.token;
            data[2] = invoice_id;
//            data[2] = Variables.invoiceId;

            PutData putData = new PutData(Variables.BaseUrl + "invoiceProducts", "POST", field, data);


            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray invoiceproductjsonArray = jsonObject.getJSONArray("result");
//
                        List<MyPaymentsInvoiceProductModel> myPaymentsInvoiceProductModels = new ArrayList<>();

                        for (int i = 0; i < invoiceproductjsonArray.length(); i++) {
                            JSONObject myPaymentsInvoiceProductModelData = invoiceproductjsonArray.getJSONObject(i);

                            String id = myPaymentsInvoiceProductModelData.getString("id");
                            String user_id = myPaymentsInvoiceProductModelData.getString("user_id");
                            String product_id = myPaymentsInvoiceProductModelData.getString("product_id");
                            String quantity = myPaymentsInvoiceProductModelData.getString("quantity");
                            String amount = myPaymentsInvoiceProductModelData.getString("amount");
                            String order_id = myPaymentsInvoiceProductModelData.getString("order_id");
                            String status = myPaymentsInvoiceProductModelData.getString("status");
                            String is_paid = myPaymentsInvoiceProductModelData.getString("is_paid");
                            String invoice_id = myPaymentsInvoiceProductModelData.getString("invoice_id");
                            String tracking_status = myPaymentsInvoiceProductModelData.getString("tracking_status");
                            String tracking_id = myPaymentsInvoiceProductModelData.getString("tracking_id");
                            String partner_name = myPaymentsInvoiceProductModelData.getString("partner_name");
                            String logged_date = myPaymentsInvoiceProductModelData.getString("logged_date");
                            String created_by = myPaymentsInvoiceProductModelData.getString("created_by");
                            String updated_by = myPaymentsInvoiceProductModelData.getString("updated_by");
                            String updated_date = myPaymentsInvoiceProductModelData.getString("updated_date");
                            String invoicenum = myPaymentsInvoiceProductModelData.getString("invoicenum");
                            String product_name = myPaymentsInvoiceProductModelData.getString("product_name");
                            String attachment = myPaymentsInvoiceProductModelData.getString("attachment");
                            String payment_type=myPaymentsInvoiceProductModelData.getString("payment_type");
//                            String name = myordersModalData.getString("name");
                            myPaymentsInvoiceProductModels.add(new MyPaymentsInvoiceProductModel(id,user_id,product_id,quantity,amount,order_id,status,is_paid,invoice_id,tracking_status,tracking_id,partner_name,payment_type,logged_date,created_by,updated_by,updated_date,invoicenum,product_name,attachment));
                        }
                        handleResponse((ArrayList<MyPaymentsInvoiceProductModel>) myPaymentsInvoiceProductModels);

                    }

                    catch (JSONException e)
                    {
                        e.printStackTrace();

                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            mListener.OnProfileItemMyPaymentsInvoiceProductsAPiGivesError(jsonObject.getString("message"));
                        }
                        catch (Exception e1)
                        {
                            e1.printStackTrace();
                            mListener.OnProfileItemMyPaymentsInvoiceProductsAPiGivesError("Internal Error");
                        }


                        handleError("Internal Error");
                    }
                } else {
                    handleError("Failed to fetch data");
                }
            }

        }


        private void handleError(String error) {
            mListener.OnProfileItemMyPaymentsInvoiceProductsAPiGivesError(error);

        }




    }

    private void handleResponse(ArrayList<MyPaymentsInvoiceProductModel> myPaymentsInvoiceProductModels) {
        mListener.OnProfileItemMyPaymentsInvoiceProductsAPiGivesSuccess(myPaymentsInvoiceProductModels);
    }

    public interface OnProfileMyPaymentsInvoiceProductApiHitFetchedListener {

        void OnProfileItemMyPaymentsInvoiceProductsAPiGivesSuccess(ArrayList<MyPaymentsInvoiceProductModel> myPaymentsInvoiceProductModels);

        void OnProfileItemMyPaymentsInvoiceProductsAPiGivesError(String error);


    }


}
