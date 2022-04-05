package com.in.apniseva.modelclass;

public class OrderItem_ModelClass {

    String categoryname,Product,Amount,subcategoryname;

    public OrderItem_ModelClass(String categoryname, String product, String amount,String subcategoryname) {
        this.categoryname = categoryname;
        Product = product;
        Amount = amount;
        subcategoryname = subcategoryname;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getSubcategoryname() {
        return subcategoryname;
    }

    public void setSubcategoryname(String subcategoryname) {
        this.subcategoryname = subcategoryname;
    }
}
