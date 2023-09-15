package com.prakruthi.ui.ui.profile.order_qty;

public class SaveOrderPurchaseQuantityQtyDataModal {

    private boolean status_code;
    private String message;

    public SaveOrderPurchaseQuantityQtyDataModal(boolean status_code, String message) {
        this.status_code = status_code;
        this.message = message;
    }

    public boolean isStatus_code() {
        return status_code;
    }

    public void setStatus_code(boolean status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
