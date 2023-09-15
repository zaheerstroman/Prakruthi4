package com.prakruthi.ui.ui.profile.mypayments;

import static com.prakruthi.ui.Variables.invoice_id;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.os.Bundle;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.InvoiceProductsApi;
import com.prakruthi.ui.APIs.MyPayments;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import java.util.ArrayList;

public class MyPaymentsInvoiceProductsActivity extends AppCompatActivity implements InvoiceProductsApi.OnProfileMyPaymentsInvoiceProductApiHitFetchedListener {

  String invoiceId;


    ShimmerRecyclerView recyclerView_MyPaymentsInvoiceProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_payments_invoice_products);

        invoiceId = getIntent().getStringExtra("invoiceId");  //invoiceId

        SetViews();
        CallApi();

    }

    private void CallApi() {
        InvoiceProductsApi invoiceProductsApis = new InvoiceProductsApi(this, invoiceId);

        invoiceProductsApis.HitProfileMyPaymentsInvoiceProductAPi();
        recyclerView_MyPaymentsInvoiceProducts.showShimmerAdapter();


    }




    private void SetViews() {
        recyclerView_MyPaymentsInvoiceProducts = findViewById(R.id.my_payments_invoice_products_recyclerview_List);

    }


    @Override
    public void OnProfileItemMyPaymentsInvoiceProductsAPiGivesSuccess(ArrayList<MyPaymentsInvoiceProductModel> myPaymentsInvoiceProductModels) {
        runOnUiThread(() -> {
            recyclerView_MyPaymentsInvoiceProducts.hideShimmerAdapter();
            recyclerView_MyPaymentsInvoiceProducts.setLayoutManager(new LinearLayoutManager(this));
            recyclerView_MyPaymentsInvoiceProducts.setAdapter(new MyPaymentsInvoicePaymentsAdaptor(myPaymentsInvoiceProductModels));


        });

    }

    @Override
    public void OnProfileItemMyPaymentsInvoiceProductsAPiGivesError(String error) {
        runOnUiThread(() -> {
            recyclerView_MyPaymentsInvoiceProducts.hideShimmerAdapter();
            PopupDialog.getInstance(this)
                    .setStyle(Styles.ALERT)
                    .setDismissButtonBackground(R.color.Primary)
                    .setHeading("Uh-Oh")
                    .setDescription("There Are No Products" +
                            " In Your Account.")
                    .setCancelable(false)
                    .showDialog(new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
        });

    }
}