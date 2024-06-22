package com.talent.authapi.dtos;

public class ProductDto {
    private String productName;
    private double price;
    private double feeRate;

    // Constructors, getters, and setters

    public ProductDto() {
    }

    public ProductDto(String productName, double price, double feeRate, String account) {
        this.productName = productName;
        this.price = price;
        this.feeRate = feeRate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(double feeRate) {
        this.feeRate = feeRate;
    }

//    public int getOrderQuantity() {
//        return orderQuantity;
//    }
//
//    public void setOrderQuantity(int orderQuantity) {
//        this.orderQuantity = orderQuantity;
//    }
}
