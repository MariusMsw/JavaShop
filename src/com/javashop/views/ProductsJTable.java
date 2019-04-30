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

    public ProductsJTable(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //
        // Creating the main frame which displays
        // products' table and login button
        //
        mainFrame = new JFrame("Shop");
        mainFrame.setSize((int) screenSize.getWidth() / 2,
                          (int) screenSize.getHeight() / 2);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        //
        // Create the Table
        //
        table = new JTable();
        table.setBounds(30, 40, 200, 300);
        JScrollPane scrollPane = new JScrollPane(table);
        mainFrame.add(scrollPane,BorderLayout.CENTER);

        //
        // Create Login button and place it under the table
        //
        loginButton = new JButton("LOGIN");
        mainFrame.add(loginButton,BorderLayout.SOUTH);

        mainFrame.setVisible(true);

    }

    public void setProductsForJTable(String[][] data){

        String[] columnNames = {"Name", "Price", "Stock"};

        DefaultTableModel tableModel = new DefaultTableModel(data,columnNames);
        table.setModel(tableModel);

    }

    public void setLoginButtonActionListener(ActionListener listener){
        loginButton.addActionListener(listener);
    }

    public JFrame getMainFrame(){
        return mainFrame;
    }

}
