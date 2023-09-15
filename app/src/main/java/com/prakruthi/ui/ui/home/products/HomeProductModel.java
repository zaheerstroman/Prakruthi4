package com.prakruthi.ui.ui.home.products;

public class HomeProductModel {

    private int id;
    private String name;
    private String attachment;
    private String rating;
    private String count_rating;
    private String actualPrice;
    private String customerPrice;
    private String dealerPrice;
    private String martPrice;
    private String description;

    public HomeProductModel(int id, String name, String attachment, String rating, String count_rating, String actualPrice, String customerPrice, String dealerPrice, String martPrice, String description) {
        this.id = id;
        this.name = name;
        this.attachment = attachment;
        this.rating = rating;
        this.count_rating = count_rating;
        this.actualPrice = actualPrice;
        this.customerPrice = customerPrice;
        this.dealerPrice = dealerPrice;
        this.martPrice = martPrice;
        this.description = description;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAttachment() {
        return attachment;
    }
    public void setAttachment(String attachment) {
        this.attachment = attachment;
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
    }    public String getRating() {
        return rating;
    }

    public String getCount_rating() {
        return count_rating;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public String getCustomerPrice() {
        return customerPrice;
    }

    public String getDealerPrice() {
        return dealerPrice;
    }

    public String getMartPrice() {
        return martPrice;
    }

}
