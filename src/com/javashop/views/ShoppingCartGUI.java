package com.javashop.views;

import com.javashop.Utils;
import com.javashop.controller.ProductsController;
import com.javashop.data.Product;
import com.javashop.data.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartGUI {

    private JTable table = new JTable();
    private JButton backButton = new JButton("Back");
    private JButton removeButton = new JButton("Remove Product");

    public ShoppingCartGUI(User user) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Map<Product,Integer> userProducts = user.getShoppingCart();

        table.setBounds(30, 40, 200, 300);

        String[][] data = ProductsController.convertProductsToData(userProducts);
        ProductsJTable.setProductsForJTable(table, data,"Name","Price","Quantity");
        JScrollPane userProductsPanel = new JScrollPane(table);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(removeButton);
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

    public void setRemoveButtonActionListener(ActionListener actionListener){
        removeButton.addActionListener(actionListener);
    }

    public JTable getTable(){
        return table;
    }

    public void refreshDataFromTable(){

        Map<Product,Integer> userProducts = Utils.loggedUser.getShoppingCart();

        String[][] data = ProductsController.convertProductsToData(userProducts);
        ProductsJTable.setProductsForJTable(table, data,"Name","Price","Quantity");

    }
}
