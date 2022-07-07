package com.example.fishmarket.utilis;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ModelCart {
    private final ArrayList<Product> cartItems = new ArrayList<>();

    public Product getProduct(int position) {
        return cartItems.get(position);
    }

    public void setProduct(Product Product) {
        cartItems.add(Product);
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

    public void addProducts(List<Product> products) {
        for (int x = 0; x < products.size(); x++) {
            Product product = products.get(x);
            if (checkData(product.getId())){
                for (int i = 0; i < cartItems.size(); i++) {
                    Product cart = cartItems.get(i);
                    if (cart.getId().equals(product.getId())) {
                        double newQuantity = cart.getQuantity() + product.getQuantity();
                        cart.setQuantity(newQuantity);
                    }
                }
            }else{
                cartItems.add(product);
            }
        }
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
