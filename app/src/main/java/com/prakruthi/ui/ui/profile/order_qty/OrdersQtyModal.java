package com.prakruthi.ui.ui.profile.order_qty;

public class OrdersQtyModal {

    String id,product_id,user_id,quantity,used_quntity,name, attachment,remaining_quntity;


    public OrdersQtyModal(String id, String product_id, String user_id, String quantity, String used_quntity, String name, String attachment, String remaining_quntity) {

        this.id = id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.quantity = quantity;
        this.used_quntity = used_quntity;
        this.name = name;
        this.attachment = attachment;
        this.remaining_quntity = remaining_quntity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUsed_quntity() {
        return used_quntity;
    }

    public void setUsed_quntity(String used_quntity) {
        this.used_quntity = used_quntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getRemaining_quntity() {
        return remaining_quntity;
    }

    public void setRemaining_quntity(String remaining_quntity) {
        this.remaining_quntity = remaining_quntity;
    }
}
