package com.prakruthi.ui.ui.profile.order_qty;

import static com.prakruthi.ui.Variables.productId;
//import static com.prakruthi.ui.Variables.quntity;
//import static com.prakruthi.ui.Variables.remaining_quntity;
//import static com.prakruthi.ui.Variables.updateData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.GetDeliveryAddressDetails;
import com.prakruthi.ui.APIs.GetOrdersQty;
import com.prakruthi.ui.APIs.OrdersQty;
import com.prakruthi.ui.APIs.SaveDeliveryAddressDetails;
import com.prakruthi.ui.APIs.SaveOrderPurchaseQuantityQtyDataApi;
import com.prakruthi.ui.HomeActivity;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.network.ApiController;
import com.prakruthi.ui.network.ApiHandler;
import com.prakruthi.ui.ui.OrderPurchaseQuantityDetails;
import com.prakruthi.ui.ui.UserDetails;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class OrderQtyActivity extends AppCompatActivity implements GetOrdersQty.OnProfileOrderQtyApiHitFetchedListener, SaveOrderPurchaseQuantityQtyDataApi.OnSaveOrderPurchaseQuantityQtyDataApiHit {

    ShimmerRecyclerView recyclerView;

    EditText AvtQtyRemainingEditText;


    AppCompatButton Update;

    AppCompatButton back;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_qty);

        AvtQtyRemainingEditText = findViewById(R.id.AvtQtyRemainingEditText);
        Update = findViewById(R.id.Update);

        //binding
        SetViews();

        //buton
        ClickListners();

        //Get
        CallApi();

        AvtQtyRemainingEditText = findViewById(R.id.AvtQtyRemainingEditText);
        Update = findViewById(R.id.Update);
    }

    @SuppressLint("UseCompatLoadingForDrawables")

    private void ClickListners() {

        back.setOnClickListener(v -> super.onBackPressed());

//        AvtQtyRemainingEditText.setText(OrderQtyDetails.remaining_quntity);
        AvtQtyRemainingEditText = findViewById(R.id.AvtQtyRemainingEditText);
        Update =findViewById(R.id.Update);

        Update.setOnClickListener(v -> {

            AvtQtyRemainingEditText = findViewById(R.id.AvtQtyRemainingEditText);


            String TextUtils = AvtQtyRemainingEditText.getText().toString();


            Log.d("OrderQtyActivity", "Button clicked");

//            if (AvtQtyRemainingEditText != null) {
            //Profile:---
//                if (TextUtils.isEmpty(AvtQtyRemainingEditText.getText())) {
            //MyAddresses:---
            boolean hasEmptyFields = false;
            if (TextUtils.isEmpty()) {
                //SearchPage
//            if (AvtQtyRemainingEditText.getText().toString().isEmpty()) {

                AvtQtyRemainingEditText.setError("Quantity cannot be empty");
                hasEmptyFields = true;
//                        hasError = true;

                ObjectAnimator.ofFloat(AvtQtyRemainingEditText, "translationX", 0, -20, 20, -10, 10, -20, 10, -20, 20, 0).start();

            }

//            }

            if (hasEmptyFields) {
                return;
            }
            //----

            //Profile:----
//            // If any EditText field is empty, return without dismissing the dialog
//            if (hasEmptyFields) {
//                return;
//            }
            //MyAddresses:----
            if (!hasEmptyFields) {
                // All fields are properly filled, perform the save operation
                SaveOrderPurchaseQuantityQtyDataApi saveOrderPurchaseQuantityQtyDataApi = new SaveOrderPurchaseQuantityQtyDataApi(this, TextUtils);
//                SaveOrderPurchaseQuantityQtyDataApi saveOrderPurchaseQuantityQtyDataApi = new SaveOrderPurchaseQuantityQtyDataApi(this, AvtQtyRemainingEditText);


                saveOrderPurchaseQuantityQtyDataApi.HitSaveOrderPurchaseQuantityQtyDataApi();
                Loading.show(this);
                // Dismiss the dialog
//                dialog.dismiss();
            }

            else {
                Log.e("OrderQtyActivity", "AvtQtyRemainingEditText is null");

                Update.setClickable(true);

            }
//            dialog.dismiss(); // Close the dialog after processing
        });

//        OrdersQty ordersQty = new OrdersQty(this);
//        ordersQty.HitAPi();

        GetOrdersQty getOrdersQty = new GetOrdersQty(this);
        getOrdersQty.execute();

    }

    public int boolToInt(boolean value) {
        return value ? 1 : 0;
    }


    public void CallApi() {
//        OrdersQty ordersQty = new OrdersQty(this);
//        ordersQty.HitAPi();
        GetOrdersQty getOrdersQty = new GetOrdersQty(this);
        getOrdersQty.execute();
        recyclerView.showShimmerAdapter();
    }

//    public void SaveOrderPurchaseQunatityQtyDataApiCallApi() {
////        SaveOrderPurchaseQuantityQtyDataApi saveOrderPurchaseQuantityQtyDataApi = new SaveOrderPurchaseQuantityQtyDataApi((SaveOrderPurchaseQuantityQtyDataApi.OnSaveOrderPurchaseQuantityQtyDataApiHit) this,updatedata);
////        SaveOrderPurchaseQuantityQtyDataApi saveOrderPurchaseQuantityQtyDataApi = new SaveOrderPurchaseQuantityQtyDataApi(this,updatedata);
////        SaveOrderPurchaseQuantityQtyDataApi saveOrderPurchaseQuantityQtyDataApi = new SaveOrderPurchaseQuantityQtyDataApi(updatedata);
////        SaveOrderPurchaseQuantityQtyDataApi saveOrderPurchaseQuantityQtyDataApi = new SaveOrderPurchaseQuantityQtyDataApi(this, updatedata);
////        SaveOrderPurchaseQuantityQtyDataApi saveOrderPurchaseQuantityQtyDataApi = new SaveOrderPurchaseQuantityQtyDataApi(productId, quntity);
////        SaveOrderPurchaseQuantityQtyDataApi saveOrderPurchaseQuantityQtyDataApi = new SaveOrderPurchaseQuantityQtyDataApi(this,productId, quntity);
////        saveOrderPurchaseQuantityQtyDataApi.HitSaveOrderPurchaseQuantityQtyDataApi();
//    }

    public void SetViews() {

        back = findViewById(R.id.search_back_btn);

        recyclerView = findViewById(R.id.my_orders_qty_recyclerview_List);

//        filters = findViewById(R.id.filters);
        AvtQtyRemainingEditText = findViewById(R.id.AvtQtyRemainingEditText);

        Update = findViewById(R.id.Update);

    }

    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }

    @Override
    public void OnProfileItemOrdersQty(ArrayList<OrdersQtyModal> ordersQtyModal) {
//    public void OnProfileItemOrdersQty() {

        try {
            runOnUiThread(() -> {

//                CallApi();

                recyclerView.hideShimmerAdapter();
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
//                recyclerView.setAdapter(new OrdersQtyAdaptor(ordersQtyModal));
                recyclerView.setAdapter(new OrdersQtyAdaptor(ordersQtyModal,this));

            });
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }

    @Override
    public void OnSaveOrderPurchaseQuantityQtyDataApiHitSuc(String messaage) {
        try {
            OrderQtyActivity.this.runOnUiThread(() -> {

                Toast.makeText(this, messaage, Toast.LENGTH_SHORT).show();



                GetOrdersQty getOrdersQty = new GetOrdersQty(this);
                getOrdersQty.execute();



                Update.setClickable(true);

                Toast.makeText(OrderQtyActivity.this, messaage, Toast.LENGTH_SHORT).show();

                Loading.hide();





                startActivity(new Intent(OrderQtyActivity.this, HomeActivity.class));

            });
        } catch (Exception ignore) {
            OrderQtyActivity.this.runOnUiThread(() -> {

//                iv_edit_http.setClickable(true);
                AvtQtyRemainingEditText.setClickable(true);

                Update.setClickable(true);

            });

        }

    }

    @Override
    public void OnSaveOrderPurchaseQuantityQtyDataApiHitError(String error) {
        OrderQtyActivity.this.runOnUiThread(() -> {
            try {
                Loading.hide();
                Toast.makeText(OrderQtyActivity.this, error, Toast.LENGTH_SHORT).show();
//                iv_edit_http.setClickable(true);
                AvtQtyRemainingEditText.setClickable(true);
                Update.setClickable(true);


            } catch (Exception ignore) {

            }
        });

    }
}