package com.javashop;

import com.javashop.controller.ProductsController;
import com.javashop.controller.ShoppingCartController;
import com.javashop.controller.UserController;
import com.javashop.data.Products;
import com.javashop.data.Users;
import com.javashop.views.ProductsJTable;
import com.javashop.views.ShoppingCartGUI;

import java.util.logging.Handler;

/*
interface setupShoppingCartController{
    public void setUpControllerAfterUsedHasLogged();
}
*/
public class Main {

    private static ProductsController controller;
    private static ShoppingCartController shoppingCartController;
    private static UserController userController;

    public static void main(String[] args) {

        Products products = Products.getInstance();

        Users users = Users.getInstance();

        ProductsJTable view = new ProductsJTable();

        controller = new ProductsController(view, products);


        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        userController = new UserController(controller.getUserGUI(), users, products,view);
                    }
                },
                4000
        );

    }


    public static void setUpControllerAfterUsedHasLogged() {

        shoppingCartController = new ShoppingCartController(userController.getShoppingCartGUI(),controller);

    }
}
