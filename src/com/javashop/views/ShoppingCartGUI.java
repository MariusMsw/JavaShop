package com.javashop.views;

import com.javashop.controller.ProductsController;
import com.javashop.data.Product;
import com.javashop.data.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShoppingCartGUI {

    public ShoppingCartGUI(User user) {
        JButton backButton = new JButton("Back");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        ArrayList<Product> userProducts = user.getShoppingCart();

        JTable table = new JTable();
        table.setBounds(30, 40, 200, 300);

        String[][] data = ProductsController.convertProductsToData(userProducts);
        ProductsJTable.setProductsForJTable(table, data);
        JScrollPane userProductsPanel = new JScrollPane(table);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);

        JSplitPane splitPane = ProductsJTable.getSplitPane();
        splitPane.setBottomComponent(bottomPanel);
        splitPane.setTopComponent(userProductsPanel);
        splitPane.setDividerLocation(((int) screenSize.getHeight() / 2) * 80 / 100);
    }
}
