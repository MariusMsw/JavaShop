package com.javashop.views;

import com.javashop.StringValues;
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
    private JButton showHistoryButton = new JButton("Transactions");
    private JTextField capitalTextField = new JTextField(StringValues.PRODUCTS_VALUE);

    public AdminGUI() {

        adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(1, 6));
        adminPanel.add(addProductInDataBaseButton);
        adminPanel.add(removeProductFromDataBaseButton);
        adminPanel.add(modifyProductButton);
        adminPanel.add(logoutButton);
        adminPanel.add(showHistoryButton);
        adminPanel.add(capitalTextField);

        capitalTextField.setEditable(false);
        capitalTextField.setHorizontalAlignment(SwingConstants.CENTER);
        capitalTextField.setFont(capitalTextField.getFont().deriveFont(Font.BOLD));

        Utils.productSelected = -1;
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

    public void setShowHistoryButtonListener(ActionListener listener) {
        this.showHistoryButton.addActionListener(listener);
    }

    public void refreshDataFromTable() {
        ArrayList<Product> theProducts = Products.getAllProducts();
        String[][] data = ProductsController.convertProductsToData(theProducts);
        ProductsJTable.setProductsForJTable(ProductsJTable.getTable(), data, "Name", "Price", "Stock");
    }

    public void updateCapitalTextField(Integer value) {
        capitalTextField.setText(StringValues.PRODUCTS_VALUE + value);
    }
}
