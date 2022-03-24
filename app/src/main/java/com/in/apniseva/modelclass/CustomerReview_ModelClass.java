package com.in.apniseva.modelclass;

public class CustomerReview_ModelClass {

    String userName,image,review,rating;

    public CustomerReview_ModelClass(String userName, String image, String review, String rating) {
        this.userName = userName;
        this.image = image;
        this.review = review;
        this.rating = rating;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
