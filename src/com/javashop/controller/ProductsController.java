package com.javashop.controller;

import com.javashop.Utils;
import com.javashop.data.*;
import com.javashop.views.AdminGUI;
import com.javashop.views.ProductsJTable;
import com.javashop.views.UserGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class ProductsController {

    private ProductsJTable view;
    private Users users;
    private Products products;

    private UserGUI userGUI = new UserGUI();
    private AdminGUI adminGUI = new AdminGUI();

    public ProductsController(ProductsJTable view, Products products) {
        this.view = view;
        this.users = Users.getInstance();
        this.products = products;

        /* Get the products from DB, convert them to String[][] and show
         * them as a table in ProductsJTable */

        ProductsJTable.setProductsForJTable(this.view.getTable(), convertProductsToData(Products.getAllProducts()),
                "Name", "Price", "Stock");
        //Set ActionListener to buttons from the main display
        this.view.setLoginButtonActionListener(new LoginButtonActionListener());
        this.view.setRegisterButtonActionListener(new RegisterButtonActionListener());
        this.view.setTableItemsListener(new TableItemMouseAdapter());
    }

    static String[][] convertProductsToData(ArrayList<Product> theProducts) {

        String[][] data;    //in data we have the products with their fields (name, price, quantity)
        if (theProducts != null) {  //if there are products
            data = new String[theProducts.size()][3];   //we create a matrix big enough to store them
        } else {
            data = new String[0][0];
        }

        int index = 0;  //the index for each product(index == number of products)

        for (Product product : Objects.requireNonNull(theProducts)) {   //for each product in ArrayList, we save in matrix their fields
            data[index][0] = product.getName();
            data[index][1] = String.valueOf(product.getPrice());
            data[index][2] = String.valueOf(product.getQuantity());

            index++;    //increase the number of products to know how many we have in DB
        }
        return data;
    }

    public static String[][] convertProductsToData(Map<Product, Integer> theProducts) {

        String[][] data;
        if (theProducts != null) {
            data = new String[theProducts.size()][3];
        } else {
            data = new String[0][0];
        }

        int index = 0;

        for (Map.Entry<Product, Integer> product : Objects.requireNonNull(theProducts).entrySet()) {

            data[index][0] = product.getKey().getName();
            data[index][1] = String.valueOf(product.getKey().getPrice());
            data[index][2] = String.valueOf(product.getValue());

            index++;
        }
        return data;
    }

    class LoginButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            JTextField username = new JTextField();
            JPasswordField password = new JPasswordField();
            final JComponent[] inputs = new JComponent[]{
                    new JLabel("Username"),
                    username,
                    new JLabel("Password"),
                    password
            };

            int result = JOptionPane.showConfirmDialog(view.getMainFrame(),
                    inputs,
                    "Login",
                    JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {

                System.out.println("You entered " +
                        username.getText() + ", " +
                        String.valueOf(password.getPassword()));
                /* when we log in with an account, search if there is that account in DB*/
                if (users.findUser(username.getText().trim(), String.valueOf(password.getPassword()).trim())) {

                    JOptionPane.showMessageDialog(view.getMainFrame(), "Successful login!");

                    /* then, check if that account is normal user or admin user to show proper GUI*/
                    if ((username.getText().trim().equals("marius") && String.valueOf(password.getPassword()).trim().equals("1234")) ||
                            (username.getText().trim().equals("bogdan") && String.valueOf(password.getPassword()).trim().equals("1234"))) {

                        view.setContent(adminGUI.getJPanel());
                    } else {
                        view.setContent(userGUI.getJPanel());
                        Utils.loggedUser = users.getUser(username.getText().trim(), String.valueOf(password.getPassword()).trim());
                    }
                } else {    //else, if there is no record of this account, alert the user
                    JOptionPane.showMessageDialog(view.getMainFrame(), "Can't connect!\n" +
                            "Try again.");
                }
            }
        }
    }

    class RegisterButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* here we create the confirm dialog with user */
            JTextField username = new JTextField();
            JPasswordField password = new JPasswordField();

            final JComponent[] inputs = new JComponent[]{
                    new JLabel("Username"),
                    username,
                    new JLabel("Password"),
                    password
            };

            int result = JOptionPane.showConfirmDialog(view.getMainFrame(),
                    inputs,
                    "REGISTER", JOptionPane.DEFAULT_OPTION
            );
            /* if user presses YES, it means that he wants to create an account*/
            if (result == JOptionPane.YES_OPTION) {
                /* so, we check if the account (username + password) already exists in DB*/
                if (users.findUser(username.getText().trim(), String.valueOf(password.getPassword()).trim())) {
                    JOptionPane.showMessageDialog(view.getMainFrame(), "User already exists!");
                } else {
                    /* If there if no matching user already, create the new account and notify the user
                     * that the account has been created*/
                    JOptionPane.showMessageDialog(view.getMainFrame(), "Successful register!");
                    Users.addUser(username.getText().trim(), String.valueOf(password.getPassword()).trim());
                }
            }
        }
    }

    class TableItemMouseAdapter extends MouseAdapter {  /* when we click on an item on products table, save
                                                           the row clicked in Utils.productSelected
                                                            to know what product we have pressed*/
        @Override
        public void mouseClicked(MouseEvent e) {
            Utils.productSelected = view.getTable().rowAtPoint(e.getPoint());
        }
    }

    public UserGUI getUserGUI() {
        return userGUI;
    }

    public AdminGUI getAdminGUI() {
        return adminGUI;
    }

    /* When back button is pressed, set back the UserGUI in bottom panel*/
    void setUserGUItoView() {
        view.setContent(userGUI.getJPanel());
    }

    public void setAdminGUIToView() {
        view.setContent(adminGUI.getJPanel());
    }

    public ProductsJTable getView() {
        return view;
    }
}
