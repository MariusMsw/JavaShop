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

public class UserController {


    private Products products;
    private Users users;

    private UserGUI view;
    private ShoppingCartGUI shoppingCartGUI;

    public UserController(UserGUI view, Users users, Products products) {

        this.view = view;
        this.users = users;
        this.products = products;

        this.view.setAddToShoppingCartButtonListener(new AddToCartButton());
        this.view.setShowShoppingCartButtonListener(new ShowSoppingCartButton());
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

    public ShoppingCartGUI getShoppingCartGUI(){
        return shoppingCartGUI;
    }
}
