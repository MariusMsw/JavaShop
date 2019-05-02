package com.javashop.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ProductsJTable {

    private JFrame mainFrame;
    private JTable table;
    private JButton loginButton;
    private JButton registerButton;
    private static JSplitPane splitPane;
    private Dimension screenSize;

    public ProductsJTable() {

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        splitPane = new JSplitPane();
        //
        // Creating the main frame which displays
        // products table, login and register buttons
        //
        mainFrame = new JFrame("Shop");
        mainFrame.setSize((int) screenSize.getWidth() / 2,
                (int) screenSize.getHeight() / 2);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);

        mainFrame.setLayout(new GridLayout());//divide the frame in 2 panels
        mainFrame.getContentPane().add(splitPane);

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(((int) screenSize.getHeight() / 2) * 80 / 100);

        JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));//the buttons panel that will be divided in 2 sides (the 2 buttons)

        //
        // show table in main panel
        //

        table = new JTable();
        table.setBounds(30, 40, 200, 300);


        JScrollPane scrollPane = new JScrollPane(table);//the panel with the products
        mainFrame.add(scrollPane, 0);
        //
        // create the 2 buttons and add them to the buttons panel
        //

        loginButton = new JButton("LOGIN");
        registerButton = new JButton("REGISTER");

        btnPnl.add(loginButton);
        btnPnl.add(registerButton);

        splitPane.setTopComponent(scrollPane);
        splitPane.setBottomComponent(btnPnl);

        mainFrame.setVisible(true);
    }

    public static void setProductsForJTable(JTable table, String[][] data) {

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

    public void setContent(JPanel panel) {

        splitPane.setBottomComponent(panel);
        splitPane.setDividerLocation(((int) screenSize.getHeight() / 2) * 80 / 100);
        mainFrame.repaint();
    }

    public void setTableItemsListener(MouseAdapter adapter) {
        table.addMouseListener(adapter);
    }

    public static JSplitPane getSplitPane() {
        return splitPane;
    }

    public JTable getTable() {
        return table;
    }

}
