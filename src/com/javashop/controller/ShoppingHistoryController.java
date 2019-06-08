package com.javashop.controller;

import com.javashop.StringValues;
import com.javashop.Utils;
import com.javashop.model.Product;
import com.javashop.model.Transaction;
import com.javashop.model.Users;
import com.javashop.views.AdminGUI;
import com.javashop.views.ProductsJTable;
import com.javashop.views.ShoppingHistoryGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

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
                JOptionPane.showMessageDialog(view.getSplitPane(), StringValues.MESSAGE_SELECT_USER);
            }
        }
    }

    private void showUserTransactionFrame(String username) {

        JFrame frame = new JFrame(username + StringValues.TEXT_USER_TRANSACTION);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) screenSize.getWidth() / 2,
                (int) screenSize.getHeight() / 2);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        ArrayList<Transaction> transactions = users.getUserTransactions(username);

        String[][] data;

        if (transactions != null) {
            data = new String[transactions.size()][5];
        } else {
            data = new String[0][0];
        }

        //the index for each product(index == number of products)
        int index = 0;

        //for each product in ArrayList, we save in matrix their fields
        for (Transaction transaction1 : Objects.requireNonNull(transactions)) {

            data[index][0] = String.valueOf(transaction1.getId());
            data[index][1] = transaction1.getProduct();
            data[index][2] = String.valueOf(transaction1.getQuantity());
            data[index][3] = transaction1.getDate();
            data[index][4] = String.valueOf(transaction1.getUserId());

            index++;
        }

        String[] columnNames = {StringValues.COLUMN_ID,
                                StringValues.COLUMN_PRODUCT_NAME,
                                StringValues.COLUMN_QUANTITY,
                                StringValues.COLUMN_DATE,
                                StringValues.COLUMN_USERID};

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable();
        table.setBounds(StringValues.TABLE_BOUNDS_X,
                StringValues.TABLE_BOUNDS_Y,
                StringValues.TABLE_BOUNDS_WIDTH,
                StringValues.TABLE_BOUNDS_HEIGHT);

        /*the panel with the transactions*/
        JScrollPane scrollPane = new JScrollPane(table);
        table.setModel(tableModel);

        JButton cancelButton = new JButton(StringValues.BUTTON_CANCEL);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(cancelButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

    }

}
