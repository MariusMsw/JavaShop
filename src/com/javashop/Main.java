package com.javashop;

import com.javashop.controller.ProductsController;
import com.javashop.controller.UserController;
import com.javashop.data.Products;
import com.javashop.data.Users;
import com.javashop.views.ProductsJTable;

import java.util.logging.Handler;

public class Main {

    public static void main(String[] args) {

        Products products = Products.getInstance();

        Users users = Users.getInstance();

        ProductsJTable view = new ProductsJTable();

        ProductsController controller = new ProductsController(view, products);

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        UserController userController = new UserController(controller.getUserGUI(),users,products);
                    }
                },
                4000
        );

    }
}
