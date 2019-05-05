package com.javashop.controller;

import com.javashop.model.Product;
import com.javashop.model.Products;
import com.javashop.model.Users;
import com.javashop.views.AdminGUI;
import com.javashop.views.ProductsJTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminController {

    private Products products;

    private AdminGUI view;
    private ProductsJTable productsJTable;

    public AdminController(Products products, AdminGUI view, ProductsJTable productsJTable) {
        this.products = products;
        this.view = view;
        this.productsJTable = productsJTable;

        this.view.setAddProductInDataBaseButtonListener(new AddProductInDBButton());
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
                int id = Integer.parseInt(productID.getText());
                String name = productName.getText();
                double price = Double.parseDouble(productPrice.getText());
                int quantity = Integer.parseInt(productQuantity.getText());
                /* create a new product with those fields, add it to the DB and refresh the table to show the product instantly in table*/
                Product product = new Product(id, name, price, quantity);
                Products.addProductToDB(product);
                JOptionPane.showMessageDialog(view.getJPanel(), "Product added with succes!");
                view.refreshDataFromTable();
            }
        }
    }
}
