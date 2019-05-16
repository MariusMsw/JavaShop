package com.javashop;

import com.javashop.controller.*;
import com.javashop.model.Products;
import com.javashop.model.Users;
import com.javashop.views.AdminGUI;
import com.javashop.views.ProductsJTable;
import com.javashop.views.ShoppingHistoryGUI;

public class Main {

    private static ProductsController controller;
    private static ShoppingCartController shoppingCartController;
    private static UserController userController;
    private static AdminController adminController;
    private static ShoppingHistoryController shoppingHistoryController;
    private static Users users;

    public static void main(String[] args) {

        Products products = Products.getInstance();

        users = Users.getInstance();

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

    public static void setUpShoppingHistoryControllerAfterAdminHasLogged(ShoppingHistoryGUI view,
                                                                         ProductsJTable productsJTable,
                                                                         AdminGUI adminGUI) {
        shoppingHistoryController = new ShoppingHistoryController(view, productsJTable, adminGUI);
    }
}
