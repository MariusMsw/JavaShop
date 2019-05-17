package com.javashop.controller;

import com.javashop.Utils;
import com.javashop.model.Transaction;
import com.javashop.model.Users;
import com.javashop.views.AdminGUI;
import com.javashop.views.ProductsJTable;
import com.javashop.views.ShoppingHistoryGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShoppingHistoryController {

    private ShoppingHistoryGUI view;
    private Users users;
    private ProductsJTable productsJTable;
    private AdminGUI adminGUI;

    public ShoppingHistoryController(ShoppingHistoryGUI view, ProductsJTable productsJTable, AdminGUI adminGUI) {
        this.view = view;
        this.productsJTable = productsJTable;
        this.adminGUI = adminGUI;
        this.users = Users.getInstance();

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

            if (view.getUsersList().getSelectedValue() != null) {

                showUserTransactionFrame(view.getUsersList().getSelectedValue().toString());

            } else {
                JOptionPane.showMessageDialog(view.getSplitPane(), "Choose a user!");
            }
        }
    }

    private void showUserTransactionFrame(String username) {

        JFrame frame = new JFrame(username + "'s transactions");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) screenSize.getWidth() / 2,
                (int) screenSize.getHeight() / 2);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        ArrayList<Transaction> transactions = users.getUserTransactions(username);
        StringBuilder transaction = new StringBuilder();

        for (Transaction object : transactions) {
            transaction.append(object.toString()).append(System.lineSeparator());
        }

        JTextArea textArea = new JTextArea(transaction.toString());
        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        panel.add(textArea, BorderLayout.CENTER);
        panel.add(cancelButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

    }

}
