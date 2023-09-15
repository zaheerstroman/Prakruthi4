package com.prakruthi.ui.ui.profile.mypayments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.os.Bundle;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.MyOrders;
import com.prakruthi.ui.APIs.MyPayments;
import com.prakruthi.ui.ui.profile.myorders.MyOrdersAdaptor;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import java.util.ArrayList;

public class MyPaymentsActivity extends AppCompatActivity implements MyPayments.OnProfileMyPaymentsApiHitFetchedListener {

    String invoice_id;
    ShimmerRecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_payments);

        invoice_id = getIntent().getStringExtra("invoice_id");


        SetViews();
        CallApi();

    }

    private void CallApi() {
        MyPayments myPayments = new MyPayments(this, invoice_id);
        myPayments.HitAPi();
        recyclerView.showShimmerAdapter();
    }

    private void SetViews() {
        recyclerView = findViewById(R.id.my_payments_recyclerview_List);
    }


    @Override
    public void OnProfileItemMyOrders(ArrayList<MyPaymentsModal> myPaymentsModals) {
        runOnUiThread( () -> {
            recyclerView.hideShimmerAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MyPaymentsAdaptor(myPaymentsModals));
        } );

    }

    @Override
    public void OnProfileItemMyPaymentsAPiGivesError(String error) {
        runOnUiThread( () -> {
            recyclerView.hideShimmerAdapter();
            PopupDialog.getInstance(this)
                    .setStyle(Styles.ALERT)
                    .setDismissButtonBackground(R.color.Primary)
                    .setHeading("Uh-Oh")
                    .setDescription("There Are No Payments"+
                            " In Your Account.")
                    .setCancelable(false)
                    .showDialog(new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
        } );

    }
}