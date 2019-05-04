package com.javashop.views;

import com.javashop.controller.ProductsController;
import com.javashop.data.Product;
import com.javashop.data.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

public class ShoppingCartGUI {

    private JButton backButton = new JButton("Back");
    private JTable table = new JTable();

    public ShoppingCartGUI(User user) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        ArrayList<Product> userProducts = user.getShoppingCart();

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

    public void setTableMouseAdapter(MouseAdapter mouseAdapter){
        this.table.addMouseListener(mouseAdapter);
    }

    public void setBackButtonActionListener(ActionListener actionListener){
        backButton.addActionListener(actionListener);
    }

    public JTable getTable(){
        return table;
    }
}
