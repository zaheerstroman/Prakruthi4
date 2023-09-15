package com.prakruthi.ui.Utility;

public class Constants {

    //CCAvenue_Payment:--
    public static final String PARAMETER_SEP = "&";
    public static final String PARAMETER_EQUALS = "=";

//    Production API URL:- https://api.ccavenue.com/apis/servlet/DoWebTrans
//    Staging API URL:- https://apitest.ccavenue.com/apis/servlet/DoWebTrans

    //    Production URL
//    public static final String TRANS_URL = "https://secure.ccavenue.com/transaction/initTrans";
    public static final String TRANS_URL = "https://api.ccavenue.com/apis/servlet/DoWebTrans";

    //Test Url
//    public static final String TRANS_URL = "https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction";
//    public static final String TRANS_URL = "https://apitest.ccavenue.com/apis/servlet/DoWebTrans";


//// UAT Account ID : 2668133

    public static final String access_code = "4YRUXLSRO20O8NIH"; // add your access_code
    public static final String merchantId = "2"; // add your merchant_id
    public static final String currency = "INR";
//    public static final String redirectUrl="http://122.182.6.216/merchant/ccavResponseHandler.jsp"; //this is test url you can modify with your on url, you can use php or jsp,
//    public static final String cancelUrl="http://122.182.6.216/merchant/ccavResponseHandler.jsp"; //this is test url you can modify with your on url
//    public static final String rsaKeyUrl="https://secure.ccavenue.com/transaction/jsp/GetRSA.jsp"; //this is test url you can modify with your on url

    public static final String redirectUrl = "http://192.168.0.241/merchant/ccavResponseHandler.jsp"; //this is test url you can modify with your on url, you can use php or jsp,
    public static final String cancelUrl = "http://192.168.0.241/merchant/ccavResponseHandler.jsp"; //this is test url you can modify with your on url
    public static final String rsaKeyUrl = "https://secure.ccavenue.com/transaction/jsp/GetRSA.jsp"; //this is test url you can modify with your on url


    //Test Card Details : Credit Card
    //Card No : 4012 0010 3714 1112
    //    CVV : 123
    //Expr. Date:
    //    Name : Test
    //     OTP :123456
}
