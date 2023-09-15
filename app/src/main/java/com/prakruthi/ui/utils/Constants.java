package com.prakruthi.ui.utils;

import static com.prakruthi.ui.network.APIClient.BASE;
import static com.prakruthi.ui.network.APIClient.BASE_URL;

public class Constants {

    //JSP 	JSP	- Java Server Pages

    //    public static final String order_id = "3881885"; // add your access_code
    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String TOKEN = "token";

    public static final String PRIVACY_POLICY_URL = BASE_URL + "privacyPolicy.html";
    public static final String PRIVACY_POLICY_URL1 = BASE + "privacy-policy-mobile.html";


    public static final String TERMS_COND_URL = BASE_URL + "termsAndConditions.html";
    public static final String TERMS_COND_URL1 = BASE + "terms-and-conditions-mobile.html";

    public static final String RETURN_REFUND_POLICY_URL1 = BASE + "return-refund-policy-mobile.html";

    public static final String SECURITY = BASE + "security-mobile.html";

    public static final String ABOUTUS = BASE + "aboutus-mobile.html";

//    public static final String PAYMENTURL = "https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction&encRequest=eyJpdiI6ImJrL0hsUTdIVGJJR2hzQ2E2MHhGQnc9PSIsInZhbHVlIjoic2RGcitHS1Y4ZUtSQ3I5U3RZQmRwMjAwbGlIc0hGVlFQS0JlL0pwWjlnWmF6L1dQSXRoOC9Fb05vMFhmay9uZU5FTW5TR2JENGd6Y2l0aDhrb1Exb0hxYXZJcEp1ZytaSEg1K1c0MFYyYlFSZXF6SG53d2ovS3NVcTVSMXZ2dGczZExleHovc2cxMm9Lc2R6aTNJSG1vRUJLeEFLUzJsZXlVbW1WODZKNmhqWDliUGFsTnl1U3Y4SnltU0o5cWs4MXo2OWh5cUhqbm9veDZxUUkwQVlVZk56ejlkUkRpZVlVOXc2QUJGbk9RbUxyZFNmdnVPMmRjTEM1MmUwbGF0Q3JzZjFjc2h2bHdEVEFHNjlsbXkwQXF4dit5WU03Zk1yRitsS0RubjMrS2MxWEVrYnRtOU9ZNDMyU0tTa2UzOGxBenVwK3ZBY29oQ0Qzd1BzMG5tRHhLZ3EvcWtPdjRaOUloaGlhREtQZHBYN3c3ck9iQ0xxWTJKc2I4c2xDYXhBdmhqeFJVWnZuOUxzcmozVFhrS2VaTkpndnpEVmx6MVZvS2tEMGJueXZ3TT0iLCJtYWMiOiJmZDA2ZGZhM2EzOTkyZjQzNmQ0ZDkzMjI2NTdlZDhjMThhNzc1MmFhOWJkYzQ5YmM3NmIxZGEyMGY4YmM2MWRmIiwidGFnIjoiIn0=&access_code=AVVN89KG36BJ08NVJB";

//    public static final String PAYMENTURL = "https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction&encRequest=b025232e2a2edf8075874c9b9bafecf0c2c432827131d047b4ca4e4438c33c204e53455d0ad649326facda92bd5483dfa55d7752d0d5e488cf4050c487fe08ad4d074afad540a38f8048443f7a75b9303b6d2dab057d80e639b0ecd541dba2b9fc16a3069fb2107fd9041fa4a2dc3917bd378101c4b7315daaab87d4205a19e7a3c4495295793c12c41b02d8d03292884936e159f146977ff0fc2a5acc383558ef3e35107d09f0a084677a0c2bd7a5e3622a315d4054d4f067d777ef7f65a60de206a7eed0d272c20dd8176c4a9385793ee6e1042bf9bf3b78989a410a102fadfcb58c179f52d02d19c0e67fcbf57b7a1b95f9e00a2035d157b92f21d546d01dabca974dded4ea1f9361e6982f632f33e4ff1fb3626c8d58f414b11f32717cab20930564d9d72e98ec57da90ccd47cc4da4b41a918b596bd0f0c54b4f837974c&access_code=AVFE97KH23AQ25EFQA";
//public static final String PAYMENTURL = "https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction&encRequest=";
public static final String PAYMENTURL = "";
//public static final String PAYMENTURL = "https://test.ccavenue.com/transaction.do?command=initiateTransaction&encRequest=enc_val&access_code=access_code";
//public static final String PAYMENTURL = "https://test.ccavenue.com/transaction.do?command=initiateTransaction&encRequest=eyJpdiI6Im9zMnptV1lEMmNqRVV3REZ2MnMwb1E9PSIsInZhbHVlIjoib2txYkw2SzVUTzVqeHN1cURLOGJKMDFlS2xkKzMzUjdDcFBRUWIvMWZ6WW5DUktLY3RSU2dxNUlHdHhxQmJPbU9MMFhjSlJwNVhyYXBwcGZKa2VNRzdqWnlkZnNvSVZEczFRc0VMS2xEWGY4NnhFTEgxU3pQR1hIbXNKVkltc2lEVjFDcFBtYVZYaHFuR3VTaDYrSlpta0RwN2N0WjllWmtPSWJlS0xHeCtwU2h3YWQ3R2RmM0dlSHZHbjZ2UHhIWk9yYU0wbzdhcXRiNVNRR01HSEFBblh2WDFPSlR6SkFMY2ROdDNvNE1lMmJPT01xQTQzdWtrbVRPcERaTXlSTHFtWXd2bnlQR1ZHZ2EwcFJQS1RSUi9nVE94bUwrUElpSnRVeS9CbGI1aHplMWpLeXdSZG9QS1BNNUttencxTk5adzZXR3VZOXlZbTNrTER2cXVUT1NtTll3TndjUURGcjl1SmFiYStyTXhGMmhnODNRN3IrRGJLa3dwZDdIeFhuV3V4ak5uN2dMc0RVZE1yVFd5Nnp0UT09IiwibWFjIjoiZmNhZDZiYzU2MDBkNGNlZjYxZTc2OTEwY2M0MTQzNGJjMmJiMTA2MDgyZmRkNDMyZThiNjdhZjFjMDUwNmJlMCIsInRhZyI6IiJ9&access_code=AVFE97KH23AQ25EFQA";
public static  String CART = "1";

    //CCAvenue_Payment:--

    public static final String PARAMETER_SEP = "&";
    public static final String PARAMETER_EQUALS = "=";

    //----------------------------------------------------------------------------------------------

    //Production API URL:-----------------------------

//    public static final String TRANS_URL = "https://secure.ccavenue.com/transaction/initTrans";
//    public static final String TRANS_URL = "https://secure.ccavenue.com/transaction/transaction.do?command=initiateTransaction";

//    HDFC PDF Production:---
//    public static final String TRANS_URL = "https://api.ccavenue.com/apis/servlet/DoWebTrans";


    //----------------------------------------------------------------------------------------------


    //Test API URL/Staging API URL:-----------------------------------

    //    Please find attached image and video file. after generating enc_val When we hit the below url getting error
//    public static final String TRANS_URL = "https://test.ccavenue.com/transaction.do?command=initiateTransaction&encRequest=enc_val&access_code=access_code";

    public static final String TRANS_URL = "";

//    public static final String TRANS_URL = "https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction&encRequest=b025232e2a2edf8075874c9b9bafecf0c2c432827131d047b4ca4e4438c33c204e53455d0ad649326facda92bd5483dfa55d7752d0d5e488cf4050c487fe08ad4d074afad540a38f8048443f7a75b9303b6d2dab057d80e639b0ecd541dba2b9fc16a3069fb2107fd9041fa4a2dc3917bd378101c4b7315daaab87d4205a19e7a3c4495295793c12c41b02d8d03292884936e159f146977ff0fc2a5acc383558ef3e35107d09f0a084677a0c2bd7a5e3622a315d4054d4f067d777ef7f65a60de206a7eed0d272c20dd8176c4a9385793ee6e1042bf9bf3b78989a410a102fadfcb58c179f52d02d19c0e67fcbf57b7a1b95f9e00a2035d157b92f21d546d01dabca974dded4ea1f9361e6982f632f33e4ff1fb3626c8d58f414b11f32717cab20930564d9d72e98ec57da90ccd47cc4da4b41a918b596bd0f0c54b4f837974c&access_code=AVFE97KH23AQ25EFQA";

//    public static final String TRANS_URL = "https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction";


    //    public static final String TRANS_URL = "https://secure.ccavenue.com/transaction/transaction.do?command=initiateTransaction&encRequest=ccee6b03e00fa4f3657cf56a57bc681341cede3be1c5a077510f239511efa413dfd5ac3dacdbe0ddd2ae6806fc9c04229be8b4535896b0cab939ccd4ec9a9560&access_code=AVVN89KG36BJ08NVJB";
    //    public static final String TRANS_URL = "https://secure.ccavenue.com/transaction/transaction.do?command=initiateTransaction&encRequest=ccee6b03e00fa4f3657cf56a57bc681341cede3be1c5a077510f239511efa413dfd5ac3dacdbe0ddd2ae6806fc9c04229be8b4535896b0cab939ccd4ec9a9560&access_code=AVGPUUZZ24BB27BBBB";

    //    Staging API URL:---
    //    HDFC Test:---
//    public static final String TRANS_URL = "https://apitest.ccavenue.com/apis/servlet/DoWebTrans";

    //------------------------------------------------------------------------------------

    //    WEB:-----------------
//    UAT Account ID : 2668133
//    public static final String access_code = "4YRUXLSRO20O8NIH"; // add your access_code
//    public static final String merchantId= "2"; // add your merchant_id
//    public static final String currency="INR";
//    public static final String redirectUrl="http://122.182.6.216/merchant/ccavResponseHandler.jsp"; //this is test url you can modify with your on url, you can use php or jsp,
//    public static final String cancelUrl="http://122.182.6.216/merchant/ccavResponseHandler.jsp"; //this is test url you can modify with your on url
//    public static final String rsaKeyUrl="https://secure.ccavenue.com/transaction/jsp/GetRSA.jsp"; //this is test url you can modify with your on url
//

    //------------------------------------------------------------

    //WEB:--------------------
//    public static final String access_code = "AVVN89KG36BJ08NVJB"; // add your access_code

//    public static final String order_id = "3881885"; // add your access_code

    //MOBILE:---

//    Status API

    //    UAT_ACCOUNT_ID(uat_account_ID) + MERCHANTID(merchantId) is Same

    //WEB ID;----
//    public static final String uat_account_ID = "2668133"; // add your access_code

    //MOBILE ID;--
    public static final String uat_account_ID = "2738600"; // add your access_code

    //    //    WEB:---
//    public static final String merchantId= "2668133"; // add your merchant_id

    //    WEB:---
    public static final String merchantId= "2738600"; // add your merchant_id

    //Mobile Integration PDF Dummy;---------
//    public static final String order_id = "3881885"; // add your access_code
//    public static final String access_code = "AVHQ89GL40BJ77QHJB"; // add your access_code

    //    WEB Cart:----
//    public static final String order_id = "3881885"; // add your access_code

    //    Dummy access_code:----
    public static final String access_code = "AVHQ89GL40BJ77QHJB"; // add your access_code

    //    WEB:---
//    public static final String access_code = "AVVN89KG36BJ08NVJB"; // add your access_code

    public static final String currency="INR";

//    public static final String redirectUrl="http://122.182.6.216/merchant/ccavResponseHandler.jsp"; //this is test url you can modify with your on url, you can use php or jsp,
//    public static final String redirectUrl="http://192.168.0.241/merchant/ccavResponseHandler.jsp";


    //HDFC PDF URL:-----
    public static final String redirectUrl="www.amazonaws.com/payment/redirect.php";

//    WEB
//    public static final String redirectUrl="http://122.182.6.216/merchant/ccavResponseHandler.jsp"; //this is test url you can modify with your on url, you can use php or jsp,
//    IPCONFIG
//    public static final String redirectUrl="http://192.168.0.241/merchant/ccavResponseHandler.jsp";

    //HDFC PDF URL:-----
    public static final String cancelUrl="www.amazonaws.com/payment/cancel.php"; //this is test url you can modify with your on url
    //    WEB
//    public static final String cancelUrl="http://122.182.6.216/merchant/ccavResponseHandler.jsp"; //this is test url you can modify with your on url
    //    IPCONFIG
//    public static final String cancelUrl="http://192.168.0.241/merchant/ccavResponseHandler.jsp"; //this is test url you can modify with your on url

    public static final String rsaKeyUrl="https://secure.ccavenue.com/transaction/jsp/GetRSA.jsp"; //this is test url you can modify with your on url

    public static final String enc_val="bbfd519ee11e197c2dd55ecd695e85ffb02ca3e4f0f3691c0f74d5d5fbed06ae8a5a17d\n" +
            "09d77ebc895133d61e208a841ffee1075ca7e75ed9f337a6d6df4138adf4cdd9de4a0f4dc98440\n" +
            "7d59db8213c4c41e91e9dc66560bba4437c4ac6892baa73114bc668fd8431b388512685d3ea";

//    public static final String enc_request="63957FB55DD6E7B968A7588763E08B240878046EF2F520C44BBC63FB9CCE726209A47348
//        77F5904445591304ABB2F5E598B951E39EAFB9A24584B00590ADB077ADE5E8C444EAC5A250B1EA96F6
//        8D22E44EA2515401C2CD753DBA91BD0E7DFE7341BE1E7B7550"&access_code="8JXENNSSBEZCU8KQ"&co
//        mmand=confirmOrder&request_type=XML&response_type=XML&version=1.1"

    //!ERROR!Required parameters not found.

//        status=0&enc_response=63957FB55DD6E7B968A7588763E08B240878046EF2F520C44BBC63FB9CCE726
//        209A473457E6B13721EC6D05ED13A0483ACFDD6F11F284AE79755D47E79687478F93CFCD3CD97510B6
//        7B961CDB5279F209F5C451F3039696F13C990B963854C8CADF730&enc_error_code=

//            status=1&enc_response=Access_code: Invalid Parameter&enc_error_code=51407.


    public static final String amount="1";


    //-----------

//    password : hdfcbank11
//
//    Merchant id : 2668133
//    Account Name : PRAKRUTHI ENTEREPRENEURS PVT LTD
//    Domain Registered for Testing : https://prakruthiepl.com/
//    Access Code : AVVN89KG36BJ08NVJB
//    Working Key : 2B4179183DAE209DD8359669978A2EAA
//
//    PRAKRUTHI ENTEREPRENEURS PVT LTD- Account ID - 2668133
//    UAT Account ID : 2668133
//    TID


    //---------

    //Test Card Details : Credit Card
    //Card No : 4012 0010 3714 1112
    //CVV : 123
    //Expr. Date:
    //Name : Test
    //OTP :123456
}
