package com.javashop.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class ProductsJTable {

    private JFrame mainFrame; /* the only frame needed in GUI*/
    private static JTable table;   /* the table with products which is shown in top panel*/
    /* login and register buttons shown in the first GUI shown*/
    private JButton loginButton;
    private JButton registerButton;

    private static JSplitPane splitPane;    /* the split pane added first in main frame which consists in top + bottom panel*/
    private JPanel buttonsPanel;
    private Dimension screenSize;

    public ProductsJTable() {

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();   /* here we get the screen size to properly show the GUI application*/
        splitPane = new JSplitPane();

         /*Creating the main frame which displays
         products table, login and register buttons*/

        mainFrame = new JFrame("Shop"); /* Create the main frame and set the dimensions for it*/
        mainFrame.setSize((int) screenSize.getWidth() * 2/3,
                (int) screenSize.getHeight() / 2);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        mainFrame.setLayout(new GridLayout());  /* we use GridLayout in main frame to display more panels*/
        mainFrame.getContentPane().add(splitPane);

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);    /* set the orientation and the separation line from top and bottom panels*/
        splitPane.setDividerLocation(((int) screenSize.getHeight() / 2) * 80 / 100);

        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));    /*the buttons panel that will be divided in 2 sides (the 2 buttons)*/

        /* here we create the table with products and add it to the main frame*/
        table = new JTable();
        table.setBounds(30, 40, 200, 300);

        JScrollPane scrollPane = new JScrollPane(table);    /*the panel with the products*/
        mainFrame.add(scrollPane, 0);

        /* create the 2 buttons and add them to the buttons panel*/

        loginButton = new JButton("LOGIN");
        registerButton = new JButton("REGISTER");

        buttonsPanel.add(loginButton);
        buttonsPanel.add(registerButton);

        splitPane.setTopComponent(scrollPane);  /* set the top component of the split panel with the products table*/
        splitPane.setBottomComponent(buttonsPanel); /* and the bottom component with the buttons panel*/

        mainFrame.setVisible(true);
    }

    public static void setProductsForJTable(JTable table, String[][] data, String column1, String column2, String column3) {
        /* here we create the GUI for the table with products by using the model
         * gotten from the DB(the products) and column names*/
        String[] columnNames = {column1, column2, column3};
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

    public void setSplitPaneBottomComponent(JPanel panel) {
        /* we set the bottom component to another panel because mostly the top component will be the panel with the products table*/
        splitPane.setBottomComponent(panel);
        splitPane.setDividerLocation(((int) screenSize.getHeight() / 2) * 80 / 100);
        mainFrame.repaint();    /* with this we refresh the main frame to show the update*/
    }

    public void setSplitPaneTopComponent(JComponent component) {

        splitPane.setTopComponent(component);
        splitPane.setDividerLocation(((int) screenSize.getHeight() / 2) * 80 / 100);
        mainFrame.repaint();
    }

    public void setTableItemsListener(MouseAdapter adapter) {
        table.addMouseListener(adapter);
    }

    static JSplitPane getSplitPane() {
        return splitPane;
    }

    public static JTable getTable() {
        return table;
    }

    public JPanel getButtonsPanel() {
        return buttonsPanel;
    }
}
