package com.prakruthi.ui.ui;

import static com.prakruthi.ui.utils.Constants.CART;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.JsonElement;
import com.prakruthi.R;
import com.prakruthi.ui.Api.API_class;
import com.prakruthi.ui.Api.Retrofit_funtion_class;
import com.prakruthi.ui.HomeActivity;
import com.prakruthi.ui.Login;
import com.prakruthi.ui.Otp_Verification_Animation;
import com.prakruthi.ui.SplashScreen;
import com.prakruthi.ui.Variables;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewActivityPayment extends AppCompatActivity {
    boolean loadingFinished = true;
    boolean redirect = false;
    ProgressDialog progressDoalog;
    String invoice_id, invoice_num;
    View include,include1;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view_payment);

        String payment_url=getIntent().getStringExtra("payment_url");
        invoice_id=getIntent().getStringExtra("invoice_id");
        invoice_num=getIntent().getStringExtra("invoice_num");

         webView=findViewById(R.id.sigup);

         include=findViewById(R.id.succss);

         include1=findViewById(R.id.failure);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view, WebResourceRequest request) {
                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                webView.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageStarted(
                    WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingFinished = false;
                //SHOW LOADING IF IT ISNT ALREADY VISIBLE
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!redirect) {
                    loadingFinished = true;
                    //HIDE LOADING IT HAS FINISHED
                    String url1=url;
                    Log.d("url1",url);
                    if (url1.equalsIgnoreCase("https://prakruthiepl.com/admin/api/paymentSucess")) {
                        paymetstaus();
                    }
                } else {
                    redirect = false;
                }
            }
        });
        webView.loadUrl(payment_url);

    }


    private void paymetstaus() {
        progressDoalog = new ProgressDialog(WebViewActivityPayment.this);
        progressDoalog.setCancelable(false);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        String ContentType = "application/json";
        String Accept = "application/json";
        final API_class service = Retrofit_funtion_class.getClient().create(API_class.class);
        Call<JsonElement> callRetrofit = null;
//        callRetrofit = service.AKRUTHTEST1(String.valueOf(Variables.id),Variables.token,invoice_num);
        callRetrofit = service.AKRUTHTEST1(String.valueOf(Variables.id),Variables.token,invoice_id,invoice_num);

        callRetrofit.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                System.out.println("----------------------------------------------------");
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                System.out.println("----------------------------------------------------");

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    String searchResponse = response.body().toString();
                    Log.d("Regisigup", searchResponse);
                    webView.setVisibility(View.GONE);
                    try {
//                        JSONObject lObj = new JSONObject(searchResponse);
                        try {
                            JSONObject lObj = new JSONObject(searchResponse);

                            String message=lObj.getString("message");
                            String status_code=lObj.getString("status_code");

                            String OrderNo=lObj.getString("Order No");
                            String Amount=lObj.getString("Amount");

                            if (status_code.equalsIgnoreCase("true")) {

                                webView.setVisibility(View.GONE);
                                include.setVisibility(View.VISIBLE);
                                include1.setVisibility(View.GONE);

                            } else {
                                include.setVisibility(View.GONE);
                                webView.setVisibility(View.GONE);
                                include1.setVisibility(View.VISIBLE);

                            }
                            CART="0";
                            new Handler().postDelayed(() -> {
                                        Intent intent = new Intent(WebViewActivityPayment.this, HomeActivity.class);
//                                        Intent intent=new Intent(WebViewActivityPayment.this, Otp_Verification_Animation.class);

                                        intent.putExtra("cart","0");
                                startActivity(intent);
                                finish();
                            },
                                    3000);

//
                        } catch (Exception e) {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    progressDoalog.dismiss();
                    if (!response.isSuccessful()) {
                        InputStream i = response.errorBody().byteStream();
                        BufferedReader r = new BufferedReader(new InputStreamReader(i));
                        StringBuilder errorResult = new StringBuilder();
                        String line;
                        try {
                            while ((line = r.readLine()) != null) {

                                errorResult.append(line).append('\n');
                                try {
                                    JSONObject jsonObject = new JSONObject(line);
                                    jsonObject.getString("message");
                                    Log.d("lineappende >>>>  ", "lineapends  >>> " + jsonObject.getString("message"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.d("line", "line" + errorResult.append("message" + line));
                                Log.d("searchResponse", "searchResponse" + errorResult.append(line).append('\n'));


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("Error Call", ">>>>" + call.toString());
                Log.d("Error", ">>>>" + t.toString());
            }
        });

    }
}