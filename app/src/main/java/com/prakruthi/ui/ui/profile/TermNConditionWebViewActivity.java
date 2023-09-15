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

public class TermNConditionWebViewActivity extends AppCompatActivity {
    private WebView webView, iv_tc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_ncondition_web_view);

//        init();

        iv_tc = findViewById(R.id.iv_tc);
        iv_tc.setWebViewClient(new WebViewClient());
        iv_tc.getSettings().setJavaScriptEnabled(true);
        iv_tc.loadUrl("https://prakruthiepl.com/terms-and-conditions-mobile.html");

        String image=getIntent().getStringExtra("Attachment");
        iv_tc.loadUrl(image);
    }

    private void init() {


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle("Terms & Conditions");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(Constants.TERMS_COND_URL));
                i.setData(Uri.parse(Constants.TERMS_COND_URL1));

                startActivity(i);
                finish();
            }
        });
        webView = findViewById(R.id.iv_tc);
        webView.getSettings().setBuiltInZoomControls(true);
    }

    @Override
    public void onBackPressed() {
        if (iv_tc.canGoBack()) {
            iv_tc.goBack();
        } else {
            super.onBackPressed();
        }
    }



}