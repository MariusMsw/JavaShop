package com.javashop.views;

import com.javashop.StringValues;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class ProductsJTable {



    /* the only frame needed in GUI*/
    private JFrame mainFrame;

    /* the table with products which is shown in top panel*/
    private static JTable table;

    /* login and register buttons shown in the first GUI shown*/
    private JButton loginButton;
    private JButton registerButton;

    /* the split pane added first in main frame which consists in top + bottom panel*/
    private static JSplitPane splitPane;

    private JPanel buttonsPanel;
    private Dimension screenSize;

    public ProductsJTable() {

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        splitPane = new JSplitPane();

         /*Creating the main frame which displays
         products table, login and register buttons*/

        mainFrame = new JFrame(StringValues.TITLE_SHOP);
        mainFrame.setSize((int) screenSize.getWidth() * 2/3,
                            (int) screenSize.getHeight() / 2);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        mainFrame.setLayout(new GridLayout());
        mainFrame.getContentPane().add(splitPane);

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(((int) screenSize.getHeight() / 2) * 80 / 100);

        /*the buttons panel that will be divided in 2 sides (the 2 buttons)*/
        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        /* here we create the table with products and add it to the main frame*/
        table = new JTable();
        table.setBounds(StringValues.TABLE_BOUNDS_X,
                        StringValues.TABLE_BOUNDS_Y,
                        StringValues.TABLE_BOUNDS_WIDTH,
                        StringValues.TABLE_BOUNDS_HEIGHT);

        /*the panel with the products*/
        JScrollPane scrollPane = new JScrollPane(table);
        mainFrame.add(scrollPane, 0);

        loginButton = new JButton(StringValues.BUTTON_LOGIN);
        registerButton = new JButton(StringValues.BUTTON_REGISTER);

        buttonsPanel.add(loginButton);
        buttonsPanel.add(registerButton);

        splitPane.setTopComponent(scrollPane);
        splitPane.setBottomComponent(buttonsPanel);

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
