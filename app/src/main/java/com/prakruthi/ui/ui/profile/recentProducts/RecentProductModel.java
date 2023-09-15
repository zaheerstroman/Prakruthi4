package com.prakruthi.ui.ui.profile.recentProducts;

public class RecentProductModel {
    String id;
    String user_id;
    String product_id;
    String logged_date;
    String created_by;
    String updated_by;
    String updated_date;
    String name;
    String attachment;
    String actual_price;
    String customer_price;
    String delar_price;
    String mart_price;
    String rating;
    String count_rating;

    public RecentProductModel(String id, String user_id, String product_id, String logged_date, String created_by, String updated_by, String updated_date, String name, String attachment, String actual_price, String customer_price, String delar_price, String mart_price, String rating, String count_rating)
    {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.logged_date = logged_date;
        this.created_by = created_by;
        this.updated_by = updated_by;
        this.updated_date = updated_date;
        this.name = name;
        this.attachment = attachment;
        this.actual_price = actual_price;
        this.customer_price = customer_price;
        this.delar_price = delar_price;
        this.mart_price = mart_price;
        this.rating = rating;
        this.count_rating = count_rating;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getLogged_date() {
        return logged_date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public String getName() {
        return name;
    }

    public String getAttachment() {
        return attachment;
    }

    public String getActual_price() {
        return actual_price;
    }

    public String getCustomer_price() {
        return customer_price;
    }

    public String getDelar_price() {
        return delar_price;
    }

    public String getMart_price() {
        return mart_price;
    }

    public String getRating() {
        return rating;
    }

    public String getCount_rating() {
        return count_rating;
    }
}
