package com.javashop.controller;

import com.javashop.Utils;
import com.javashop.model.Users;
import com.javashop.views.AdminGUI;
import com.javashop.views.ProductsJTable;
import com.javashop.views.ShoppingHistoryGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingHistoryController {

    private ShoppingHistoryGUI view;
    private Users users;
    private ProductsJTable productsJTable;
    private AdminGUI adminGUI;

    public ShoppingHistoryController(ShoppingHistoryGUI view, ProductsJTable productsJTable, AdminGUI adminGUI) {
        this.view = view;
        this.productsJTable = productsJTable;
        this.adminGUI = adminGUI;

        this.view.setBackButtonListener(new BackButtonActionListener());
        this.view.setShowTransactionsButtonListener(new ShowTransactionsActionListener());
    }

    public class BackButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            productsJTable.setSplitPaneTopComponent(ProductsJTable.getTable());
            productsJTable.setSplitPaneBottomComponent(adminGUI.getJPanel());
        }
    }

    public class ShowTransactionsActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if ( view.getUsersList().getSelectedValue() != null) {
                String userName = view.getUsersList().getSelectedValue().toString();
                JFrame frame = new JFrame( userName + "'s transactions");

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setSize((int) screenSize.getWidth()  / 2,
                        (int) screenSize.getHeight() / 2);
                JPanel panel = new JPanel();
                JTextField textField = new JTextField("dfadfas");

                panel.add(textField);

                frame.add(panel);
                frame.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(view.getSplitPane(), "Choose a user!");
            }
        }
    }
}
