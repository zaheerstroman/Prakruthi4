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

public class AboutUsWebViewActivity extends AppCompatActivity {

    private WebView webView, iv_attachment_About_us_http;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_web_view);

        iv_attachment_About_us_http = findViewById(R.id.iv_attachment_About_us_http);

        webView = findViewById(R.id.iv_attachment_About_us_http);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://prakruthiepl.com/aboutus-mobile.html");


        String image=getIntent().getStringExtra("Attachment");
        iv_attachment_About_us_http.loadUrl(image);
    }

    private void init() {


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle("AboutUs");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Constants.ABOUTUS));

                startActivity(i);
                finish();
            }
        });
        iv_attachment_About_us_http = findViewById(R.id.iv_attachment_About_us_http);
        iv_attachment_About_us_http.getSettings().setBuiltInZoomControls(true);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}