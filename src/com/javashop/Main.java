package com.javashop;

import com.javashop.controller.AdminController;
import com.javashop.controller.ProductsController;
import com.javashop.controller.ShoppingCartController;
import com.javashop.controller.UserController;
import com.javashop.model.Products;
import com.javashop.model.Users;
import com.javashop.views.ProductsJTable;

public class Main {

    private static ProductsController controller;
    private static ShoppingCartController shoppingCartController;
    private static UserController userController;
    private static AdminController adminController;

    public static void main(String[] args) {

        Products products = Products.getInstance();

        Users users = Users.getInstance();

        ProductsJTable view = new ProductsJTable();

        controller = new ProductsController(view, products);

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        userController = new UserController(controller.getUserGUI(), users, products, view);
                        adminController = new AdminController(products, controller.getAdminGUI(), view);
                    }
                },
                4000
        );
    }

    public static void setUpControllerAfterUsedHasLogged() {

        shoppingCartController = new ShoppingCartController(userController.getShoppingCartGUI(), controller);
    }
}
