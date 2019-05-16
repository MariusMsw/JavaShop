package com.javashop.controller;

import com.javashop.InputValidation;
import com.javashop.Main;
import com.javashop.Utils;
import com.javashop.model.Product;
import com.javashop.model.Products;
import com.javashop.views.AdminGUI;
import com.javashop.views.ProductsJTable;
import com.javashop.views.ShoppingHistoryGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AdminController {

    private Products products;

    private AdminGUI view;
    private ShoppingHistoryGUI shoppingHistoryGUI;

    private ProductsJTable productsJTable;

    public AdminController(Products products, AdminGUI view, ProductsJTable productsJTable) {
        this.products = products;
        this.view = view;
        this.productsJTable = productsJTable;

        this.view.updateCapitalTextField(products.calculateCapital());

        this.view.setAddProductInDataBaseButtonListener(new AddProductInDBButton());
        this.view.setRemoveProductFromDataBaseButtonListener(new RemoveProductFromDBButton());
        this.view.setModifyProductButtonListener(new ModifyProductInDB());
        this.view.setLogoutButtonListener(new LogoutButton());
        this.view.setShowHistoryButtonListener(new TransactionsButton());
    }

    public class AddProductInDBButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* get the product fields from the user*/
            JTextField productID = new JTextField();
            JTextField productName = new JTextField();
            JTextField productPrice = new JTextField();
            JTextField productQuantity = new JTextField();

            final JComponent[] inputs = new JComponent[]{
                    new JLabel("ID"),
                    productID,
                    new JLabel("Name"),
                    productName,
                    new JLabel("Price"),
                    productPrice,
                    new JLabel("Quantity"),
                    productQuantity
            };

            int result = JOptionPane.showConfirmDialog(view.getJPanel(),
                    inputs,
                    "Add product",
                    JOptionPane.YES_NO_OPTION);
            /*if Yes button is clicked, it means that the product should be added to the DB*/
            if (result == JOptionPane.YES_OPTION) {

                /*and so, we get the fields inserted and cast them to the specific type*/
                try {
                    int id = Integer.parseInt(productID.getText());
                    String name = productName.getText();
                    double price = Double.parseDouble(productPrice.getText());
                    int quantity = Integer.parseInt(productQuantity.getText());

                    if (InputValidation.isProductIDValid(id) && price >= 0 && quantity >= 0) {
                        /* create a new product with those fields, add it to the DB and refresh the table to show the product instantly in table*/
                        Product product = new Product(id, name, price, quantity);
                        Products.addProductToDB(product);

                        view.refreshDataFromTable();
                        view.updateCapitalTextField(products.calculateCapital());

                        JOptionPane.showMessageDialog(view.getJPanel(), "Product added with success!");
                    } else {
                        JOptionPane.showMessageDialog(view.getJPanel(), "Can't add this product!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view.getJPanel(), "Write normal values!");
                }
            }
        }
    }

    public class RemoveProductFromDBButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (Utils.productSelected == -1) {
                /* if there is no product selected, there is nothing
                to remove from DB and so, we notify the user*/

                JOptionPane.showMessageDialog(view.getJPanel(), "Please select a product!");

            } else {
                /*else, if there is a product selected, we remove the product
                from ArrayList and from DB and refresh the panel with products table from the DB*/

                Products.removeProductFromDB(Objects.requireNonNull(Products.getProductAt(Utils.productSelected)));

                view.refreshDataFromTable();
                view.updateCapitalTextField(products.calculateCapital());

                JOptionPane.showMessageDialog(view.getJPanel(), "The product has been removed!");

                Utils.productSelected = -1;
            }
        }
    }

    public class ModifyProductInDB implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Utils.productSelected == -1) {

                /* if there is no product selected, there is nothing
                to modify in DB and so, we notify the user*/

                JOptionPane.showMessageDialog(view.getJPanel(), "Please select a product!");

            } else {

                /*else, if there is a product selected, we modify the product
                in ArrayList and in DB and refresh the panel with products table from the DB*/
                /*the id of the selected product, to know what to modify in DB*/
                int selectedID = Objects.requireNonNull(Products.getProductAt(Utils.productSelected)).getId();

                JTextField productName = new JTextField();
                JTextField productPrice = new JTextField();
                JTextField productQuantity = new JTextField();

                final JComponent[] inputs = new JComponent[]{
                        new JLabel("Name"),
                        productName,
                        new JLabel("Price"),
                        productPrice,
                        new JLabel("Quantity"),
                        productQuantity
                };

                int result = JOptionPane.showConfirmDialog(view.getJPanel(),
                        inputs,
                        "Modify product",
                        JOptionPane.YES_NO_OPTION);

                /*if Yes button is clicked, it means that the product should be modified in the DB*/
                if (result == JOptionPane.YES_OPTION) {
                    /*and so, we get the fields inserted and cast them to the specific type*/
                    try {
                        String name = productName.getText();
                        double price = Double.parseDouble(productPrice.getText());
                        int quantity = Integer.parseInt(productQuantity.getText());

                        if (!name.equals("") && price >= 0 && quantity >= 0) {

                            /* create a new product with those fields, modify it in the DB and refresh the table to show the product instantly in table*/
                            Product product = new Product(selectedID, name, price, quantity);
                            Products.modifyProductInDB(product);

                            view.refreshDataFromTable();
                            view.updateCapitalTextField(products.calculateCapital());

                            JOptionPane.showMessageDialog(view.getJPanel(), "Product modified with success!");

                            Utils.productSelected = -1;
                        } else {
                            JOptionPane.showMessageDialog(view.getJPanel(), "Can't modify this product!");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view.getJPanel(), "Write normal values!");
                    }
                }
            }
        }
    }

    public class LogoutButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(view.getJPanel(), "Logout successful!");
            productsJTable.setSplitPaneBottomComponent(productsJTable.getButtonsPanel());
        }
    }

    public class TransactionsButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            shoppingHistoryGUI = new ShoppingHistoryGUI();
            Main.setUpShoppingHistoryControllerAfterAdminHasLogged(shoppingHistoryGUI, productsJTable, view);
        }
    }
}
