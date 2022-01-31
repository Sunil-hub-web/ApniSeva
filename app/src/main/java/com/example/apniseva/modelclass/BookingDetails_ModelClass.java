package com.example.apniseva.modelclass;

public class BookingDetails_ModelClass {

    String bookingId,bookingStatues,servicesName,servicesType,price;

    public BookingDetails_ModelClass(String bookingId, String bookingStatues, String servicesName,
                                     String servicesType, String price) {
        this.bookingId = bookingId;
        this.bookingStatues = bookingStatues;
        this.servicesName = servicesName;
        this.servicesType = servicesType;
        this.price = price;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingStatues() {
        return bookingStatues;
    }

    public void setBookingStatues(String bookingStatues) {
        this.bookingStatues = bookingStatues;
    }

    public String getServicesName() {
        return servicesName;
    }

    public void setServicesName(String servicesName) {
        this.servicesName = servicesName;
    }

    public String getServicesType() {
        return servicesType;
    }

    public void setServicesType(String servicesType) {
        this.servicesType = servicesType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
