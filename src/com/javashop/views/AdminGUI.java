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
    private JButton addProductInDataBaseButton = new JButton(StringValues.BUTTON_ADD_PRODUCT);
    private JButton removeProductFromDataBaseButton = new JButton(StringValues.BUTTON_REMOVE_PRODUCT);
    private JButton modifyProductButton = new JButton(StringValues.BUTTON_MODIFY_PRODUCT);
    private JButton logoutButton = new JButton(StringValues.BUTTON_LOGOUT);
    private JButton showHistoryButton = new JButton(StringValues.BUTTON_SHOW_HISTORY);
    private JTextField capitalTextField = new JTextField(StringValues.PRODUCTS_VALUE);

    public AdminGUI() {

        adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(StringValues.ADMIN_GUI_ROWS, StringValues.ADMIN_GUI_COLUMNS));
        adminPanel.add(addProductInDataBaseButton);
        adminPanel.add(removeProductFromDataBaseButton);
        adminPanel.add(modifyProductButton);
        adminPanel.add(logoutButton);
        adminPanel.add(showHistoryButton);
        adminPanel.add(capitalTextField);

        capitalTextField.setEditable(false);
        capitalTextField.setHorizontalAlignment(SwingConstants.CENTER);
        capitalTextField.setFont(capitalTextField.getFont().deriveFont(Font.BOLD));

        Utils.productSelected = StringValues.NO_PRODUCT_SELECTED;
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
        ProductsJTable.setProductsForJTable(ProductsJTable.getTable(),
                                            data,
                                            StringValues.COLUMN_NAME,
                                            StringValues.COLUMN_PRICE,
                                            StringValues.COLUMN_PRICE);
    }

    public void updateCapitalTextField(Integer value) {
        capitalTextField.setText(StringValues.PRODUCTS_VALUE + value);
    }
}
