package com.javashop.controller;

import com.javashop.StringValues;
import com.javashop.Utils;
import com.javashop.model.Product;
import com.javashop.model.Products;
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

        Utils.productSelected = -1;
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
             * we set the products to display them by converting them to model. So we can show them
             * in a table in top panel*/
            ProductsJTable.setProductsForJTable(view.getTable(), ProductsController.convertProductsToData(Products.getAllProducts()),
                                                StringValues.COLUMN_NAME,
                                                StringValues.COLUMN_PRICE,
                                                StringValues.COLUMN_STOCK);

            Utils.productSelected = StringValues.NO_PRODUCT_SELECTED;

             /* with this line, we set the bottom panel back to UserGUI when back
             button is pressed from show cart interface*/
            productsController.setUserGUItoView();
        }
    }

    class TableItemMouseAdapter extends MouseAdapter {
        /* when we press on an item in table, save the number of row
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

            if (Utils.productSelected == StringValues.NO_PRODUCT_SELECTED) {

                /* if there is no product selected, there is nothing
                to remove from shopping cart and so, we notify the user*/

                JOptionPane.showMessageDialog(view.getTable(), StringValues.MESSAGE_SELECT_PRODUCT);

            } else {
                /*else, if there is a product selected, we remove the product from HashMap
                and refresh the panel with products table from the shopping cart*/

                Utils.loggedUser.removeFromShoppingCart(Utils.loggedUser.getProductAtIndex(Utils.productSelected));

                view.refreshDataFromTable();
                view.updateSum();

                JOptionPane.showMessageDialog(view.getTable(), StringValues.MESSAGE_SUCCESSFUL_PRODUCT_REMOVE);
            }
        }
    }
}
