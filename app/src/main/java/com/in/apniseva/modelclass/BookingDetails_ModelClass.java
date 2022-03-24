package com.in.apniseva.modelclass;

import java.util.ArrayList;

public class BookingDetails_ModelClass {

    String order_id,price,subtotal,name,address,address1,mobile,create_user_id,work_status,
            book_pay_status,categoryname;
    ArrayList<OrderItem_ModelClass> orderitem;

    public BookingDetails_ModelClass(String order_id, String price, String subtotal, String name,
                                     String address, String address1, String mobile, String create_user_id,
                                     String work_status, String book_pay_status,
                                     ArrayList<OrderItem_ModelClass> orderitem,String categoryname) {
        this.order_id = order_id;
        this.price = price;
        this.subtotal = subtotal;
        this.name = name;
        this.address = address;
        this.address1 = address1;
        this.mobile = mobile;
        this.create_user_id = create_user_id;
        this.work_status = work_status;
        this.book_pay_status = book_pay_status;
        this.orderitem = orderitem;
        this.categoryname = categoryname;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreate_user_id() {
        return create_user_id;
    }

    public void setCreate_user_id(String create_user_id) {
        this.create_user_id = create_user_id;
    }

    public String getWork_status() {
        return work_status;
    }

    public void setWork_status(String work_status) {
        this.work_status = work_status;
    }

    public String getBook_pay_status() {
        return book_pay_status;
    }

    public void setBook_pay_status(String book_pay_status) {
        this.book_pay_status = book_pay_status;
    }

    public ArrayList<OrderItem_ModelClass> getOrderitem() {
        return orderitem;
    }

    public void setOrderitem(ArrayList<OrderItem_ModelClass> orderitem) {
        this.orderitem = orderitem;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    @Override
    public String toString() {
        return "BookingDetails_ModelClass{" +
                "order_id='" + order_id + '\'' +
                ", price='" + price + '\'' +
                ", subtotal='" + subtotal + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", address1='" + address1 + '\'' +
                ", mobile='" + mobile + '\'' +
                ", create_user_id='" + create_user_id + '\'' +
                ", work_status='" + work_status + '\'' +
                ", book_pay_status='" + book_pay_status + '\'' +
                ", categoryname='" + categoryname + '\'' +
                ", orderitem=" + orderitem +
                '}';
    }
}
