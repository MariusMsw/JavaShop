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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class ShoppingCartController {

    private ShoppingCartGUI view;
    private Map<Product,Integer> shoppingCart;

    // used when user goes back to products' view
    // in BackButtonActionListener class
    private ProductsController productsController;

    public ShoppingCartController(ShoppingCartGUI shoppingCartGUI, ProductsController productsController){

         this.shoppingCart = Utils.loggedUser.getShoppingCart();
         this.view = shoppingCartGUI;
         this.productsController = productsController;

         this.view.setBackButtonActionListener(new BackButtonActionListener());
         this.view.setTableMouseAdapter(new TableItemMouseAdapter());
         this.view.setRemoveButtonActionListener(new RemoveProductButtonActionListener());
    }

    class BackButtonActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            ProductsJTable.setProductsForJTable(view.getTable(),ProductsController.convertProductsToData(Products.getAllProducts()),
                                                "Name","Price","Stock");
            productsController.setUserGUItoView();

        }
    }

    class TableItemMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {

            Utils.productSelected = view.getTable().rowAtPoint(e.getPoint());

        }
    }

    class RemoveProductButtonActionListener implements ActionListener{


        @Override
        public void actionPerformed(ActionEvent e) {


            if (Utils.productSelected == -1) {
                JOptionPane.showMessageDialog(view.getTable(), "Please select a product!");
            } else {

                Utils.loggedUser.removeFromShoppingCart(Utils.loggedUser.getProductAtIndex(Utils.productSelected));
                view.refreshDataFromTable();
                JOptionPane.showMessageDialog(view.getTable(), "The product has been removed!");
            }
        }
    }
}
