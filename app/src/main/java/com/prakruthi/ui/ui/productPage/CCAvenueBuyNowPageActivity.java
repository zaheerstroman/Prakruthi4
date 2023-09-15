package com.prakruthi.ui.ui.productPage;

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

public class CCAvenueBuyNowPageActivity extends AppCompatActivity {

    private WebView webView, iv_attachment_ccavenue_buynow;

    public static String payment_url, paymentUrl,invoice_id,invoiceId,invoice_num,invoiceNum,status_code,statusCode,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ccavenue_buy_now_page);

        iv_attachment_ccavenue_buynow = findViewById(R.id.iv_attachment_ccavenue_buynow);

        webView = findViewById(R.id.iv_attachment_ccavenue_buynow);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);


//        webView.loadUrl("https://prakruthiepl.com/aboutus-mobile.html");
//        webView.loadUrl("https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction&encRequest=eyJpdiI6ImJrL0hsUTdIVGJJR2hzQ2E2MHhGQnc9PSIsInZhbHVlIjoic2RGcitHS1Y4ZUtSQ3I5U3RZQmRwMjAwbGlIc0hGVlFQS0JlL0pwWjlnWmF6L1dQSXRoOC9Fb05vMFhmay9uZU5FTW5TR2JENGd6Y2l0aDhrb1Exb0hxYXZJcEp1ZytaSEg1K1c0MFYyYlFSZXF6SG53d2ovS3NVcTVSMXZ2dGczZExleHovc2cxMm9Lc2R6aTNJSG1vRUJLeEFLUzJsZXlVbW1WODZKNmhqWDliUGFsTnl1U3Y4SnltU0o5cWs4MXo2OWh5cUhqbm9veDZxUUkwQVlVZk56ejlkUkRpZVlVOXc2QUJGbk9RbUxyZFNmdnVPMmRjTEM1MmUwbGF0Q3JzZjFjc2h2bHdEVEFHNjlsbXkwQXF4dit5WU03Zk1yRitsS0RubjMrS2MxWEVrYnRtOU9ZNDMyU0tTa2UzOGxBenVwK3ZBY29oQ0Qzd1BzMG5tRHhLZ3EvcWtPdjRaOUloaGlhREtQZHBYN3c3ck9iQ0xxWTJKc2I4c2xDYXhBdmhqeFJVWnZuOUxzcmozVFhrS2VaTkpndnpEVmx6MVZvS2tEMGJueXZ3TT0iLCJtYWMiOiJmZDA2ZGZhM2EzOTkyZjQzNmQ0ZDkzMjI2NTdlZDhjMThhNzc1MmFhOWJkYzQ5YmM3NmIxZGEyMGY4YmM2MWRmIiwidGFnIjoiIn0=&access_code=AVVN89KG36BJ08NVJB");
//        webView.loadUrl("https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction&encRequest=b025232e2a2edf8075874c9b9bafecf0c2c432827131d047b4ca4e4438c33c204e53455d0ad649326facda92bd5483dfa55d7752d0d5e488cf4050c487fe08ad4d074afad540a38f8048443f7a75b9303b6d2dab057d80e639b0ecd541dba2b9fc16a3069fb2107fd9041fa4a2dc3917bd378101c4b7315daaab87d4205a19e7a3c4495295793c12c41b02d8d03292884936e159f146977ff0fc2a5acc383558ef3e35107d09f0a084677a0c2bd7a5e3622a315d4054d4f067d777ef7f65a60de206a7eed0d272c20dd8176c4a9385793ee6e1042bf9bf3b78989a410a102fadfcb58c179f52d02d19c0e67fcbf57b7a1b95f9e00a2035d157b92f21d546d01dabca974dded4ea1f9361e6982f632f33e4ff1fb3626c8d58f414b11f32717cab20930564d9d72e98ec57da90ccd47cc4da4b41a918b596bd0f0c54b4f837974c&access_code=AVFE97KH23AQ25EFQA");
//        webView.loadUrl("https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction&encRequest=");

//        webView.loadUrl("");
        webView.loadUrl("payment_url");
//        webView.loadUrl("paymentUrl");
        webView.loadUrl("invoice_id");
        webView.loadUrl("invoice_num");
        webView.loadUrl("status_code");
        webView.loadUrl("message");

        webView.loadUrl(paymentUrl);
        webView.loadUrl(invoiceId);
        webView.loadUrl(invoiceNum);
        webView.loadUrl(statusCode);
        webView.loadUrl(message);


        // Get dynamic URLs from intent
        String paymentUrl = getIntent().getStringExtra("payment_url");
//        String dynamicInvoiceId = getIntent().getStringExtra("invoice_id");
        String invoiceId = getIntent().getStringExtra("invoice_id");

        String invoiceNum = getIntent().getStringExtra("invoice_num");
        String statusCode = getIntent().getStringExtra("status_code");
        String message = getIntent().getStringExtra("message");




//        String image = getIntent().getStringExtra("payment_url");
//        String image = getIntent().getStringExtra("payment_url");
//        String image=getIntent().getStringExtra("paymentUrl");
//        iv_attachment_ccavenue_buynow.loadUrl(paymentUrl);
        iv_attachment_ccavenue_buynow.loadUrl(paymentUrl);
        iv_attachment_ccavenue_buynow.loadUrl(invoiceId);
        iv_attachment_ccavenue_buynow.loadUrl(invoiceNum);
        iv_attachment_ccavenue_buynow.loadUrl(statusCode);
        iv_attachment_ccavenue_buynow.loadUrl(message);

        if (paymentUrl != null) {
            webView.loadUrl(paymentUrl);
        }
        else if (invoiceId != null) {
            webView.loadUrl(invoiceId);
        }
        else if (invoiceNum != null) {
            webView.loadUrl(invoiceNum);
        }
        else if (statusCode != null) {
            webView.loadUrl(statusCode);
        }
        else if (message != null) {
            webView.loadUrl(message);
        }
        else
        {
            // Handle the case when paymentUrl is null or empty
//            showError("Payment URL is invalid");
            webView.loadUrl("Payment URL is invalid");

        }

//        init();


    }

    private void init() {


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle("CCAvenue Buy Now Page");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Constants.PAYMENTURL));

                startActivity(i);
                finish();
            }
        });
        iv_attachment_ccavenue_buynow = findViewById(R.id.iv_attachment_ccavenue_buynow);
        iv_attachment_ccavenue_buynow.getSettings().setBuiltInZoomControls(true);
    }

}