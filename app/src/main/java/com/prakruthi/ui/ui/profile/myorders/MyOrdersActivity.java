package com.prakruthi.ui.ui.profile.myorders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.MyOrders;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity implements MyOrders.OnProfileMyOrdersApiHitFetchedListener {

    ShimmerRecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        SetViews();
        CallApi();
    }

    public void CallApi()
    {
        MyOrders myOrders = new MyOrders(this);
        myOrders.HitAPi();
        recyclerView.showShimmerAdapter();
    }
    public void SetViews()
    {
        recyclerView = findViewById(R.id.my_orders_recyclerview_List);
    }
    @Override
    public void OnProfileItemMyOrders(ArrayList<MyOrdersModal> myOrdersModals) {
        runOnUiThread( () -> {
            recyclerView.hideShimmerAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MyOrdersAdaptor(myOrdersModals));
        } );
    }

    @Override
    public void OnProfileItemMyOrdersAPiGivesError(String error) {
        runOnUiThread( () -> {
            recyclerView.hideShimmerAdapter();
            PopupDialog.getInstance(this)
                    .setStyle(Styles.ALERT)
                    .setDismissButtonBackground(R.color.Primary)
                    .setHeading("Uh-Oh")
                    .setDescription("There Are No Orders"+
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