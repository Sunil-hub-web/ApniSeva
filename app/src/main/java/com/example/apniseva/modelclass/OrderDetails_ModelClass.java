package com.example.apniseva.modelclass;

public class OrderDetails_ModelClass {

    String orderName,price;

    public OrderDetails_ModelClass(String orderName, String price) {
        this.orderName = orderName;
        this.price = price;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
