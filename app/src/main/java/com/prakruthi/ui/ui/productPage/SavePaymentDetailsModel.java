package com.prakruthi.ui.ui.productPage;

public class SavePaymentDetailsModel {

    private static String paymentUrl;

    private static String invoiceId;
    private static String invoiceNum;
    private static Boolean statusCode;
    private static String message;

    public SavePaymentDetailsModel(String paymentUrl, String invoiceId,String invoiceNum, Boolean statusCode, String message) {

        this.paymentUrl = paymentUrl;
        this.invoiceId = String.valueOf(invoiceId);
        this.invoiceNum= String.valueOf(invoiceNum);
        this.statusCode = statusCode;
        this.message = message;


    }





    public static String getPaymentUrl() {
        return paymentUrl;
    }

    public static void setPaymentUrl(String paymentUrl) {
        SavePaymentDetailsModel.paymentUrl = paymentUrl;
    }

    public static String getInvoiceId() {
        return invoiceId;
    }


    public static void setInvoiceId(String invoiceId) {
        SavePaymentDetailsModel.invoiceId = invoiceId;
    }



    public static String getInvoiceNum() {
        return invoiceNum;
    }

    public static void setInvoiceNum(String invoiceNum) {
        SavePaymentDetailsModel.invoiceNum = invoiceNum;
    }

    public Boolean getStatusCode() {
        return statusCode;
    }

    public static void setStatusCode(Boolean statusCode) {

        SavePaymentDetailsModel.statusCode = statusCode;

    }

    public String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        SavePaymentDetailsModel.message = message;

    }
}


//--------------------------------------------

//package com.prakruthi.ui.ui.productPage;
//
//public class SavePaymentDetailsModel {
//
//    private String paymentUrl;
//
//    private Integer invoiceId;
//
//    private Boolean statusCode;
//
//    private String message;
//
//
//    public SavePaymentDetailsModel(String paymentUrl, Integer invoiceId, Boolean statusCode, String message) {
//        this.paymentUrl = paymentUrl;
//        this.invoiceId = invoiceId;
//        this.statusCode = statusCode;
//        this.message = message;
//    }
//
//    public String getPaymentUrl() {
//        return paymentUrl;
//    }
//
//    public void setPaymentUrl(String paymentUrl) {
//        this.paymentUrl = paymentUrl;
//    }
//
//    public Integer getInvoiceId() {
//        return invoiceId;
//    }
//
//    public void setInvoiceId(Integer invoiceId) {
//        this.invoiceId = invoiceId;
//    }
//
//    public Boolean getStatusCode() {
//        return statusCode;
//    }
//
//    public void setStatusCode(Boolean statusCode) {
//        this.statusCode = statusCode;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//}
