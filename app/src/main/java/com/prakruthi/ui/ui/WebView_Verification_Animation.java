package com.prakruthi.ui.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.prakruthi.R;

public class WebView_Verification_Animation extends AppCompatActivity {

    ImageView gif_webview_verify_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_verification_animation);

        gif_webview_verify_image = findViewById(R.id.gif_webview_verify_image);

        //encResp
        //orderNo
        //crossSellUrl
        //invoice_num
    }
}