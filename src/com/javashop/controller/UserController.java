package com.javashop.controller;

import com.javashop.Main;
import com.javashop.Utils;
import com.javashop.model.Products;
import com.javashop.model.Users;
import com.javashop.views.ProductsJTable;
import com.javashop.views.ShoppingCartGUI;
import com.javashop.views.UserGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        this.view.setShowShoppingCartButtonListener(new ShowSoppingCartButton());
        this.view.setCheckoutButtonListener(new CheckoutButton());
        this.view.setLogoutButtonListener(new LogoutButton());
    }

    public ShoppingCartGUI getShoppingCartGUI() {
        return shoppingCartGUI;
    }

    private void refreshProductsInTable() {
        /* if there are products in table, we read again the products from shopping cart (HashMap) and show them in interface*/
        if (shoppingCartGUI.getTable() != null) {
            ProductsJTable.setProductsForJTable(shoppingCartGUI.getTable(), ProductsController.convertProductsToData(Products.getAllProducts()),
                    "Name", "Price", "Stock");
        } else {
            ProductsJTable.setProductsForJTable(productsJTable.getTable(), ProductsController.convertProductsToData(Products.getAllProducts()),
                    "Name", "Price", "Stock");
        }
    }

    class AddToCartButton implements ActionListener {
        /* when we press Add Product button, we use the productSelected that holds the row of
         * the product in DB and, if it is -1, it means that no product has been selected*/
        @Override
        public void actionPerformed(ActionEvent e) {

            if (Utils.productSelected == -1) {
                JOptionPane.showMessageDialog(view.getJPanel(), "Please select a product!");
            } else {/* but, if it is different from -1, it means that a product has been selected,
                        so we add the product from that row in shopping cart( HashMap)*/
                users.addProductToShoppingCart(products.getProductAt(Utils.productSelected), Utils.loggedUser);
                JOptionPane.showMessageDialog(view.getJPanel(), "The product has been added!");
            }
        }
    }

    class ShowSoppingCartButton implements ActionListener {
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

            if (Utils.loggedUser.isShoppingCartEmpty()) {/*if so, alert the user and do nothing*/
                JOptionPane.showMessageDialog(view.getJPanel(), "The shopping cart is empty!");
            } else  {/*else, if there are products in shopping cart(HashMap),
                      we remove the products one by one in DB, alert the user that everything is ok*/
                Products.removeProductsFromDB(Utils.loggedUser.getShoppingCart());
                JOptionPane.showMessageDialog(view.getJPanel(), "Checkout successful!");
                Utils.loggedUser.emptyShoppingCart();   /*clear all the products in shopping cart(in HashMap)*/
                refreshProductsInTable();   /* and refresh the table (read again the products from DB and display them
                                                in UserGUI*/
            }
        }
    }

    class LogoutButton implements ActionListener {
        /* when we click on Logout button, show the first screen with bottom panel consist of the 2 buttons:
         * login and register*/

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(view.getJPanel(), "Logout successful!");
            productsJTable.setContent(productsJTable.getButtonsPanel());
        }
    }
}
