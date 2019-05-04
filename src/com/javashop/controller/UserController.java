package com.javashop.controller;

import com.javashop.Main;
import com.javashop.Utils;
import com.javashop.data.Products;
import com.javashop.data.User;
import com.javashop.data.Users;
import com.javashop.views.ProductsJTable;
import com.javashop.views.ShoppingCartGUI;
import com.javashop.views.UserGUI;
import com.sun.source.doctree.UnknownInlineTagTree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.javashop.controller.ProductsController.convertProductsToData;

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
        this.view.setCheckoutButtonListener(new CheckoutButtonListenerButton());
    }

    public ShoppingCartGUI getShoppingCartGUI(){
        return shoppingCartGUI;
    }

    public void refreshProductsInTable(){

        if(shoppingCartGUI.getTable() != null) {

            ProductsJTable.setProductsForJTable(shoppingCartGUI.getTable(), ProductsController.convertProductsToData(Products.getAllProducts()),
                                            "Name", "Price", "Stock");
        }else {
            ProductsJTable.setProductsForJTable(productsJTable.getTable(), ProductsController.convertProductsToData(Products.getAllProducts()),
                    "Name", "Price", "Stock");
        }
    }

    class AddToCartButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (Utils.productSelected == -1) {
                JOptionPane.showMessageDialog(view.getJPanel(), "Please select a product!");
            } else {
                users.addProductToShoppingCart(products.getProductAt(Utils.productSelected), Utils.loggedUser);
                JOptionPane.showMessageDialog(view.getJPanel(), "The product has been added!");
            }
        }

    }

    class ShowSoppingCartButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            shoppingCartGUI = new ShoppingCartGUI(Utils.loggedUser);

            //
            // Callback function to setup ShoppingCartController
            //
            Main.setUpControllerAfterUsedHasLogged();

        }

    }

    class CheckoutButtonListenerButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if(Utils.loggedUser.isShoppingCartEmpty()) {
                JOptionPane.showMessageDialog(view.getJPanel(), "The shopping cart is empty!");
            }else {
                Products.removeProductFromDB(Utils.loggedUser.getShoppingCart());
                JOptionPane.showMessageDialog(view.getJPanel(), "Check-out successful!");
                Utils.loggedUser.emptyShoppingCart();
                refreshProductsInTable();

                System.out.println("click pe check-out!");
            }
        }
    }


}
