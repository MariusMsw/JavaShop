package com.javashop.data;

import java.util.ArrayList;

public class User {

    private int id;
    private String username;
    private String password;
    private ArrayList<Product> shoppingCart = new ArrayList<>();

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ArrayList<Product> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void addToShoppingCart(Product product){
        shoppingCart.add(product);
    }

    public int getProductQuantity(Product product){

        int quantity = 0;

        for(Product prod : shoppingCart){
            if(prod.getName().equals(product.getName())){
                quantity++;
            }
        }

        return quantity;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';

    }
}
