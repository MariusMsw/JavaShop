package com.javashop.controller;

import com.javashop.Utils;
import com.javashop.data.Product;
import com.javashop.data.Products;
import com.javashop.views.ProductsJTable;
import com.javashop.views.ShoppingCartGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class ShoppingCartController {

    private ShoppingCartGUI view;
    private Map<Product, Integer> shoppingCart;

    /* we use this to get back to first display when back button is pressed
     * from show cart button*/
    private ProductsController productsController;

    public ShoppingCartController(ShoppingCartGUI shoppingCartGUI, ProductsController productsController) {

        this.shoppingCart = Utils.loggedUser.getShoppingCart(); //get the logged user shopping cart
        this.view = shoppingCartGUI;
        this.productsController = productsController;

        this.view.setBackButtonActionListener(new BackButtonActionListener());
        this.view.setTableMouseAdapter(new TableItemMouseAdapter());
        this.view.setRemoveButtonActionListener(new RemoveProductButtonActionListener());
    }

    class BackButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* when back button is pressed from show shopping cart interface,
             * we set the products to display them by converting them to data. So we can show them
             * in a table in top panel*/
            ProductsJTable.setProductsForJTable(view.getTable(), ProductsController.convertProductsToData(Products.getAllProducts()),
                    "Name", "Price", "Stock");
            productsController.setUserGUItoView();  /* with this line, we set the bottom panel back to UserGUI when back
                                                        button is pressed from show cart interface*/
        }
    }

    class TableItemMouseAdapter extends MouseAdapter {  /* when we press on an item in table, save the number of row
                                                            to know on what product we have clicked*/

        @Override
        public void mouseClicked(MouseEvent e) {
            Utils.productSelected = view.getTable().rowAtPoint(e.getPoint());
        }
    }

    class RemoveProductButtonActionListener implements ActionListener {
        /*when we press the remove product button, remove that product from user shopping cart
         * and from table in ShoppingCart interface*/
        @Override
        public void actionPerformed(ActionEvent e) {

            if (Utils.productSelected == -1) {  /* if there is no product selected, there is nothing to remove from shopping cart
                                                   and so, we notify the user*/
                JOptionPane.showMessageDialog(view.getTable(), "Please select a product!");
            } else {    /*else, if there is a product selected, we remove the product from HashMap
                          and refresh the panel with products table from the shopping cart*/
                Utils.loggedUser.removeFromShoppingCart(Utils.loggedUser.getProductAtIndex(Utils.productSelected));
                view.refreshDataFromTable();
                JOptionPane.showMessageDialog(view.getTable(), "The product has been removed!");
            }
        }
    }
}
