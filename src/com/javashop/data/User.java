package com.javashop.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private int id;
    private String username;
    private String password;
    private Map<Product,Integer> shoppingCart = new HashMap<>();

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

    public Map<Product,Integer> getShoppingCart() {
        return shoppingCart;
    }

    public void addToShoppingCart(Product product){

        if(!shoppingCart.containsKey(product)){
            shoppingCart.put(product,1);
        }else {
            int quantity = shoppingCart.get(product);
            shoppingCart.remove(product);
            shoppingCart.put(product,++quantity);
        }
    }

    public void removeFromShoppingCart(Product product){

        if(shoppingCart.containsKey(product)){

            int quantity = shoppingCart.get(product);
            shoppingCart.remove(product);
            quantity--;
            if(quantity > 0) {
                shoppingCart.put(product,quantity);
            }
        }
    }

    public Product getProductAtIndex(int index){

        int i = -1;
        for (Map.Entry<Product,Integer> product : shoppingCart.entrySet()){
            i++;
            if(i == index){
                return product.getKey();
            }
        }
        return null;
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
