package com.prakruthi.ui.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.prakruthi.R;
import com.prakruthi.ui.utils.Constants;

public class ReturnRefundPolicyActivity extends AppCompatActivity {

    private WebView webView, iv_rrf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_refund_policy);

        iv_rrf = findViewById(R.id.iv_rrf);
        iv_rrf.setWebViewClient(new WebViewClient());
        iv_rrf.getSettings().setBuiltInZoomControls(true);
        iv_rrf.loadUrl("https://prakruthiepl.com/return-refund-policy-mobile.html");


    }

    private void init() {



        iv_rrf = findViewById(R.id.iv_rrf);
        iv_rrf.setWebViewClient(new WebViewClient());
        iv_rrf.getSettings().setBuiltInZoomControls(true);
        iv_rrf.loadUrl("https://prakruthiepl.com/return-refund-policy-mobile.html");

    }

    @Override
    public void onBackPressed() {
        if (iv_rrf.canGoBack()) {
            iv_rrf.goBack();
        } else {
            super.onBackPressed();
        }
    }

}