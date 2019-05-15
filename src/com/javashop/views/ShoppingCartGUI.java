package com.javashop.views;

import com.javashop.Utils;
import com.javashop.controller.ProductsController;
import com.javashop.model.Product;
import com.javashop.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Map;

public class ShoppingCartGUI {

    /* the table with the shopping cart products*/
    /* the two buttons from she shopping cart interface: back and remove product*/
    private JTable table = new JTable();

    private JButton backButton = new JButton("Back");
    private JButton removeButton = new JButton("Remove Product");
    private JTextField totalSum = new JTextField();

    public ShoppingCartGUI(User user) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Map<Product, Integer> userProducts = user.getShoppingCart();
        table.setBounds(30, 40, 200, 300);

        /* convert the shopping cart products (the HashMap) to matrix with each product fields:
         *name of product, price and quantity to show that product in table properly*/
        String[][] data = ProductsController.convertProductsToData(userProducts);

        /* change the Stock column to Quantity because we are in shopping cart GUI*/
        ProductsJTable.setProductsForJTable(table, data, "Name", "Price", "Quantity");
        JScrollPane userProductsPanel = new JScrollPane(table);

        totalSum.setText("Total sum: " + Utils.loggedUser.calculateSumToPay());
        totalSum.setEditable(false);
        totalSum.setHorizontalAlignment(SwingConstants.CENTER);

        /* the panel with the buttons from shopping cart GUI (back + remove product buttons)*/
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(removeButton);
        bottomPanel.add(backButton);
        bottomPanel.add(totalSum);

        /* the split pane that contains top + bottom panel
        and set the proper components in it:
        table with products on top and buttons on bottom*/

        JSplitPane splitPane = ProductsJTable.getSplitPane();
        splitPane.setBottomComponent(bottomPanel);
        splitPane.setTopComponent(userProductsPanel);
        splitPane.setDividerLocation(((int) screenSize.getHeight() / 2) * 80 / 100);
    }

    public void setTableMouseAdapter(MouseAdapter mouseAdapter) {
        this.table.addMouseListener(mouseAdapter);
    }

    public void setBackButtonActionListener(ActionListener actionListener) {
        backButton.addActionListener(actionListener);
    }

    public void setRemoveButtonActionListener(ActionListener actionListener) {
        removeButton.addActionListener(actionListener);
    }

    public JTable getTable() {
        return table;
    }

    public void refreshDataFromTable() {
        /* after we update the products in shopping cart (add or remove), we need to refresh the products from table and show
         * the products in shopping cart in table*/
        Map<Product, Integer> userProducts = Utils.loggedUser.getShoppingCart();
        String[][] data = ProductsController.convertProductsToData(userProducts);
        ProductsJTable.setProductsForJTable(table, data, "Name", "Price", "Quantity");
    }

    public void updateSum(){
        totalSum.setText("Total sum: " + Utils.loggedUser.calculateSumToPay());
    }
}
