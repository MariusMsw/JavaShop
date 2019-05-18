package com.javashop.controller;

import com.javashop.Main;
import com.javashop.StringValues;
import com.javashop.Utils;
import com.javashop.model.Product;
import com.javashop.model.Products;
import com.javashop.model.Transaction;
import com.javashop.model.Users;
import com.javashop.views.ProductsJTable;
import com.javashop.views.ShoppingCartGUI;
import com.javashop.views.UserGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class UserController {

    private Products products;
    private Users users;

    private UserGUI view;
    private ShoppingCartGUI shoppingCartGUI;
    private ProductsJTable productsJTable;

    public UserController(UserGUI view, Users users, Products products, ProductsJTable productsJTable) {

        this.view = view;
        this.users = users;
        this.products = products;
        this.productsJTable = productsJTable;

        this.view.setAddToShoppingCartButtonListener(new AddToCartButton());
        this.view.setShowShoppingCartButtonListener(new ShowShoppingCartButton());
        this.view.setCheckoutButtonListener(new CheckoutButton());
        this.view.setLogoutButtonListener(new LogoutButton());
    }

    public ShoppingCartGUI getShoppingCartGUI() {
        return shoppingCartGUI;
    }

    private void refreshProductsInTable() {
        /* if there are products in table, we read again the products from shopping cart (HashMap) and show them in interface*/
        if (shoppingCartGUI != null && shoppingCartGUI.getTable() != null) {

            ProductsJTable.setProductsForJTable(shoppingCartGUI.getTable(), ProductsController.convertProductsToData(Products.getAllProducts()),
                                                StringValues.COLUMN_NAME,
                                                StringValues.COLUMN_PRICE,
                                                StringValues.COLUMN_STOCK);
        } else {
            ProductsJTable.setProductsForJTable(productsJTable.getTable(), ProductsController.convertProductsToData(Products.getAllProducts()),
                                                StringValues.COLUMN_NAME,
                                                StringValues.COLUMN_PRICE,
                                                StringValues.COLUMN_STOCK);
        }
    }

    class AddToCartButton implements ActionListener {

        /* when we press Add Product button, we use the productSelected that holds the row of
         * the product in DB and, if it is -1, it means that no product has been selected*/

        @Override
        public void actionPerformed(ActionEvent e) {

            if (Utils.productSelected == StringValues.NO_PRODUCT_SELECTED) {
                JOptionPane.showMessageDialog(view.getJPanel(), StringValues.MESSAGE_SELECT_PRODUCT);
            } else {
                /* but, if it is different from -1, it means that a product has been selected,
                so we add the product from that row in shopping cart( HashMap)*/

                users.addProductToShoppingCart(Products.getProductAt(Utils.productSelected), Utils.loggedUser);
                JOptionPane.showMessageDialog(view.getJPanel(), StringValues.MESSAGE_SUCCESSFUL_PRODUCT_ADD);
            }
        }
    }

    class ShowShoppingCartButton implements ActionListener {
        /* when we press show shopping cart button, we create a new interface for shopping cart for efficiency*/
        @Override
        public void actionPerformed(ActionEvent e) {

            shoppingCartGUI = new ShoppingCartGUI(Utils.loggedUser);

            /* Callback function to setup ShoppingCartController*/

            Main.setUpControllerAfterUsedHasLogged();
        }
    }

    class CheckoutButton implements ActionListener {
        /* when we press checkout button, we check if the shopping cart is empty(there is no product in HashMap)*/
        @Override
        public void actionPerformed(ActionEvent e) {

            if (Utils.loggedUser.isShoppingCartEmpty()) {

                /*if so, alert the user and do nothing*/
                JOptionPane.showMessageDialog(view.getJPanel(), StringValues.MESSAGE_SHOPPING_CART_EMPTY);

            } else if (Utils.loggedUser.getMoney() >= Utils.loggedUser.calculateSumToPay()) {

                /*else, if there are products in shopping cart(HashMap),
                we remove the products one by one in DB, alert the user that everything is ok*/

                Products.removeProductsFromDB(Utils.loggedUser.getShoppingCart());
                JOptionPane.showMessageDialog(view.getJPanel(), StringValues.MESSAGE_SUCCESSFUL_CHECKOUT);

                ArrayList<Transaction> userTransactions = users.saveTransactionOfUser(Utils.loggedUser);

                for (Transaction transaction : userTransactions) {
                    users.updateTransactionInDB(transaction);
                }

                /*clear all the products in shopping cart(in HashMap)*/
                Utils.loggedUser.setMoney(Utils.loggedUser.getMoney() - Utils.loggedUser.calculateSumToPay());
                users.updateUserMoneyInDB(Utils.loggedUser);
                Utils.loggedUser.emptyShoppingCart();

                view.setMoneyTextField(Utils.loggedUser.getMoney());

                /* and refresh the table (read again the products
                from DB and display them in UserGUI*/
                refreshProductsInTable();


            } else {
                JOptionPane.showMessageDialog(view.getJPanel(), StringValues.MESSAGE_SARAC_DETECTED);
            }
        }
    }

    class LogoutButton implements ActionListener {
        /* when we click on Logout button, show the first screen with bottom panel consist of the 2 buttons:
         * login and register*/

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(view.getJPanel(), StringValues.MESSAGE_SUCCESSFUL_LOGOUT);
            productsJTable.setSplitPaneBottomComponent(productsJTable.getButtonsPanel());
        }
    }
}
