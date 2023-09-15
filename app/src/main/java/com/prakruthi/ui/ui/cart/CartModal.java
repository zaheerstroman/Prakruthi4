package com.prakruthi.ui.ui.cart;


import java.util.ArrayList;

public class CartModal {

    public static int cartAmount;

//    public static int tax;
    public static int cgst;
    public static int sgst;
    public static int igst;
    public static int cgst_percentage;
    public static int sgst_percentage;
    public static int igst_percentage;
    public static int subTotal;

    private int id;
    private int productId;
    private int quantity;
    private String name;
    private String description;
    private String customerPrice;

    private String actualPrice;
    private String dealerPrice;

    private String martPrice;
    private String attachment;

    // Constructor
    public CartModal(int id, int productId, int quantity, String name, String description, String customerPrice, String actualPrice, String dealerPrice, String martPrice, String attachment) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.name = name;
        this.description = description;

        this.customerPrice = customerPrice;
        this.actualPrice = actualPrice;
        this.dealerPrice = dealerPrice;
        this.martPrice = martPrice;

        this.attachment = attachment;
    }

    // Getters
    public int getId() {
        return id;
    }


    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCustomerPrice() {
        return customerPrice;
    }

    public static int getCartAmount() {
        return cartAmount;
    }

//    public static int getTax() {
//        return tax;
//    }
//
//    public static void setTax(int tax) {
//        CartModal.tax = tax;
//    }

    public static void setCartAmount(int cartAmount) {
        CartModal.cartAmount = cartAmount;
    }

    public static int getCgst() {
        return cgst;
    }

    public static void setCgst(int cgst) {
        CartModal.cgst = cgst;
    }

    public static int getSgst() {
        return sgst;
    }

    public static void setSgst(int sgst) {
        CartModal.sgst = sgst;
    }

    public static int getIgst() {
        return igst;
    }

    public static void setIgst(int igst) {
        CartModal.igst = igst;
    }

    public static int getCgst_percentage() {
        return cgst_percentage;
    }

    public static void setCgst_percentage(int cgst_percentage) {
        CartModal.cgst_percentage = cgst_percentage;
    }

    public static int getSgst_percentage() {
        return sgst_percentage;
    }

    public static void setSgst_percentage(int sgst_percentage) {
        CartModal.sgst_percentage = sgst_percentage;
    }

    public static int getIgst_percentage() {
        return igst_percentage;
    }

    public static void setIgst_percentage(int igst_percentage) {
        CartModal.igst_percentage = igst_percentage;
    }

    public static int getSubTotal() {
        return subTotal;
    }

    public static void setSubTotal(int subTotal) {
        CartModal.subTotal = subTotal;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public String getDealerPrice() {
        return dealerPrice;
    }

    public String getMartPrice() {
        return martPrice;
    }

    public String getAttachment() {
        return attachment;
    }
}


