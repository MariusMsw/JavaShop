package com.javashop.views;

import com.javashop.data.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProductsJTable {

    private JFrame mainFrame;
    private JTable table;
    private JButton loginButton;
    private JButton registerButton;

    public ProductsJTable() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //
        // Creating the main frame which displays
        // products' table and login button
        //
        mainFrame = new JFrame("Shop");
        mainFrame.setSize((int) screenSize.getWidth() / 2,
                (int) screenSize.getHeight() / 2);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER));//divide the frame in 2 panels

        JPanel mainPanel = new JPanel(new BorderLayout());//the main panel, with the products
        JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));//the buttons panel that will be divided in 2 sides (the 2 buttons)

        //
        // show table in main panel
        //

        table = new JTable();
        table.setBounds(30, 40, 200, 300);
        JScrollPane scrollPane = new JScrollPane(table);
        mainFrame.add(scrollPane, BorderLayout.WEST);
        mainPanel.add(btnPnl, BorderLayout.EAST);

        //
        // create the 2 buttons and add them to the buttons panel
        //

        loginButton = new JButton("LOGIN");
        registerButton = new JButton("REGISTER");

        btnPnl.add(loginButton);
        btnPnl.add(registerButton);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    public void setProductsForJTable(String[][] data) {

        String[] columnNames = {"Name", "Price", "Stock"};

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        table.setModel(tableModel);

    }

    public void setLoginButtonActionListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void setRegisterButtonActionListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

}
