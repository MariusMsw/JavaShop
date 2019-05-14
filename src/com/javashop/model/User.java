package com.javashop.model;

import java.util.HashMap;
import java.util.Map;

public class User {

    private int id;
    private String username;
    private String password;
    private Map<Product, Integer> shoppingCart = new HashMap<>();
    private Integer money;

    public User(String username, String password, Integer money) {
        this.username = username;
        this.password = password;
        this.money = money;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    public Map<Product, Integer> getShoppingCart() {
        return shoppingCart;
    }

    void addToShoppingCart(Product product) {
        /*when we add a product to shopping cart, check if there is already that product there,
         * if so, we increase the quantity of that product,
         * else, we add that product to shopping cart with quantity as 1*/
        if (!shoppingCart.containsKey(product)) {
            shoppingCart.put(product, 1);
        } else {
            int quantity = shoppingCart.get(product);
            shoppingCart.remove(product);
            shoppingCart.put(product, ++quantity);
        }
    }

    public void removeFromShoppingCart(Product product) {
        /* as above, when we remove a product from shopping cart, we check if there is that product in shopping cart,
         * if so, decrease the quantity by 1,
         * else, do nothing*/
        if (shoppingCart.containsKey(product)) {

            int quantity = shoppingCart.get(product);
            shoppingCart.remove(product);
            quantity--;
            if (quantity > 0) {
                shoppingCart.put(product, quantity);
            }
        }
    }

    public Product getProductAtIndex(int index) {
        /* here we return the product which is find at a specified index in DB*/
        int i = -1;
        for (Map.Entry<Product, Integer> product : shoppingCart.entrySet()) {
            i++;
            if (i == index) {
                return product.getKey();
            }
        }
        return null;
    }

    public void emptyShoppingCart() {   /* utility method to dispose all the products from shopping cart
                                            when checkout button is pressed*/
        shoppingCart.clear();
    }

    public boolean isShoppingCartEmpty() {  /*here we check if there are products in shopping cart*/
        return shoppingCart.isEmpty();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
