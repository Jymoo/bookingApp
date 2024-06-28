package com.example.bookapp;

import android.net.Uri;

public class Orders {

    private String imageUri;
    private String productName;
    private String amount;
    private String location;

    public Orders(String imageUri, String productName, String amount, String location) {
        this.imageUri = imageUri;
        this.productName = productName;
        this.amount = amount;
        this.location = location;
    }


    public Orders (){}

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "imageUri='" + imageUri + '\'' +
                ", productName='" + productName + '\'' +
                ", amount='" + amount + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}