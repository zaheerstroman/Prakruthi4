package com.prakruthi.ui.ui.productPage.productReviews;

public class ProductReviewsModel {
    String rating,review;

    public String getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public ProductReviewsModel(String rating, String review) {
        this.rating = rating;
        this.review = review;
    }
}
