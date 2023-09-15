package com.prakruthi.ui.ui.wishlist;

public class WishListModal {

    private int wishlistId;

    private String name;

    private String description;

    private String customerPrice;

    private String actualPrice;
    private String delarPrice;

    private String martPrice;
    private String color;

    private String size;

    private String type;

    private String attachment;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    private String product_id;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    private String date;


    public WishListModal(int wishlistId, String name, String description, String customerPrice, String actualPrice,String delarPrice,String martPrice,String color, String size, String type, String attachment , String product_id, String date) {
        this.wishlistId = wishlistId;
        this.name = name;
        this.description = description;

        this.customerPrice = customerPrice;
        this.actualPrice = actualPrice;
        this.delarPrice = delarPrice;
        this.martPrice = martPrice;

        this.color = color;
        this.size = size;
        this.type = type;
        this.attachment = attachment;
        this.product_id = product_id;
        this.date = date;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomerPrice() {
        return customerPrice;
    }

    public void setCustomerPrice(String customerPrice) {
        this.customerPrice = customerPrice;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getDelarPrice() {
        return delarPrice;
    }

    public void setDelarPrice(String dealerPrice) {
        this.delarPrice = dealerPrice;
    }

    public String getMartPrice() {
        return martPrice;
    }

    public void setMartPrice(String martPrice) {
        this.martPrice = martPrice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}