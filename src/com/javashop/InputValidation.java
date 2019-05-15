package com.javashop;

import com.javashop.model.Product;
import com.javashop.model.Products;
import com.javashop.model.User;
import com.javashop.model.Users;

public class InputValidation {

    private static Users users = Users.getInstance();
    private static Products products = Products.getInstance();

    public static boolean isUserIDValid(int id) {

        if (id < 0) {
            return false;
        }
        for (User user : Users.getUsers()) {
            if (id == user.getId()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isProductIDValid(int id) {

        if (id < 0) {
            return false;
        }
        for (Product product : Products.getAllProducts()) {
            if (id == product.getId()) {
                return false;
            }
        }
        return true;
    }
}
