package com.javashop.views;

import com.javashop.Utils;
import com.javashop.controller.ProductsController;
import com.javashop.model.Product;
import com.javashop.model.Products;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class AdminGUI {

    private JPanel adminPanel;
    private JButton addProductInDataBaseButton = new JButton("Add product");
    private JButton removeProductFromDataBaseButton = new JButton("Remove product");
    private JButton modifyProductButton = new JButton("Modify product");
    private JButton logoutButton = new JButton("Logout");

    public AdminGUI() {

        adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(1, 4));
        adminPanel.add(addProductInDataBaseButton);
        adminPanel.add(removeProductFromDataBaseButton);
        adminPanel.add(modifyProductButton);
        adminPanel.add(logoutButton);
    }

    public JPanel getJPanel() {
        /* the panel with admin buttons: add, remove, modify product and logout (bottom one)*/
        return adminPanel;
    }

    public void setAddProductInDataBaseButtonListener(ActionListener listener) {
        this.addProductInDataBaseButton.addActionListener(listener);
    }

    public void setRemoveProductFromDataBaseButtonListener(ActionListener listener) {
        this.removeProductFromDataBaseButton.addActionListener(listener);
    }

    public void setModifyProductButtonListener(ActionListener listener) {
        this.modifyProductButton.addActionListener(listener);
    }

    public void setLogoutButtonListener(ActionListener listener) {
        this.logoutButton.addActionListener(listener);
    }

    public void refreshDataFromTable() {
        ArrayList<Product> theProducts = Products.getAllProducts();
        String[][] data = ProductsController.convertProductsToData(theProducts);
        ProductsJTable.setProductsForJTable(ProductsJTable.getTable(), data, "Name", "Price", "Stock");
    }
}
