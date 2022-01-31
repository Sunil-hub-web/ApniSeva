package com.example.apniseva.modelclass;

public class RecommendedServices_ModelClass {

    String image,service_name,price;

    public RecommendedServices_ModelClass(String image, String service_name, String price) {
        this.image = image;
        this.service_name = service_name;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
