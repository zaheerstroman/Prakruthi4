package com.prakruthi.ui.ui.profile.mypayments;

public class MyPaymentsModal {

    String id;
    String user_id;
    String invoice_id;

    String total;
    String invoicenum;
    String transaction_id;
    String cf_transaction_id;
    String payment_mode;
    String payment_date;
    String is_paid;


    public MyPaymentsModal(String id, String user_id, String invoice_id, String total,String invoicenum, String transaction_id, String cf_transaction_id, String payment_mode, String payment_date, String is_paid) {
        this.id = id;
        this.user_id = user_id;
        this.invoice_id = invoice_id;
        this.total = total;
        this.invoicenum = invoicenum;
        this.transaction_id = transaction_id;
        this.cf_transaction_id = cf_transaction_id;
        this.payment_mode = payment_mode;
        this.payment_date = payment_date;
        this.is_paid = is_paid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getCf_transaction_id() {
        return cf_transaction_id;
    }

    public void setCf_transaction_id(String cf_transaction_id) {
        this.cf_transaction_id = cf_transaction_id;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(String is_paid) {
        this.is_paid = is_paid;
    }


    public String getInvoicenum() {
        return invoicenum;
    }

    public void setInvoicenum(String invoicenum) {
        this.invoicenum = invoicenum;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
