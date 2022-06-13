package com.example.fishmarket.utilis;

import java.util.ArrayList;

public class ModelCart {
    private final ArrayList<Product> cartItems = new ArrayList<>();

    public Product getProduct(int position) {
        return cartItems.get(position);
    }

    public void setProduct(Product Products) {
        cartItems.add(Products);
    }

    public int getCartSize() {
        return cartItems.size();
    }

    public void clearArray() {
        cartItems.clear();
    }

    public void removeProduct(int position) {
        cartItems.remove(position);
    }

    public ArrayList<Product> getCart() {
        return cartItems;
    }

    public int getTotalFee() {
        int totalFee = 0;
        for (int i = 0; i < cartItems.size(); i++) {
            totalFee += cartItems.get(i).getQuantity() * cartItems.get(i).getPrice();
        }
        return totalFee;
    }

    public int getTotalItem() {
        int totalItem = 0;
        for (int i = 0; i < cartItems.size(); i++) {
            totalItem += cartItems.get(i).getQuantity();
        }
        return totalItem;
    }

    public boolean checkData(String idProduct) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getId().equals(idProduct)) {
                return true;
            }
        }
        return false;
    }

}
