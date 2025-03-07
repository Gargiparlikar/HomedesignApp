package com.homedesignhub.controllers;

public class CartItem {
    private String imageUrl;
    private String price;
    private String type;

    public CartItem(String imageUrl, String price, String type) {
        this.imageUrl = imageUrl;
        this.price = price;
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public String getItemType() {
        return type;
    }

  
}
