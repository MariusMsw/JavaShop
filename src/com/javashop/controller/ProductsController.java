package com.javashop.controller;

import com.javashop.StringValues;
import com.javashop.Utils;
import com.javashop.model.*;
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

        ProductsJTable.setProductsForJTable(ProductsJTable.getTable(), convertProductsToData(Products.getAllProducts()),
                                            StringValues.COLUMN_NAME,
                                            StringValues.COLUMN_PRICE,
                                            StringValues.COLUMN_STOCK);

        //Set ActionListener to buttons from the main display
        this.view.setLoginButtonActionListener(new LoginButtonActionListener());
        this.view.setRegisterButtonActionListener(new RegisterButtonActionListener());
        this.view.setTableItemsListener(new TableItemMouseAdapter());
    }

    public static String[][] convertProductsToData(ArrayList<Product> theProducts) {

        String[][] data;

        if (theProducts != null) {
            data = new String[theProducts.size()][3];
        } else {
            data = new String[0][0];
        }

        //the index for each product(index == number of products)
        int index = 0;

        //for each product in ArrayList, we save in matrix their fields
        for (Product product : Objects.requireNonNull(theProducts)) {

            data[index][0] = product.getName();
            data[index][1] = String.valueOf(product.getPrice());
            data[index][2] = String.valueOf(product.getQuantity());

            index++;
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
                    new JLabel(StringValues.LABEL_USERNAME),
                    username,
                    new JLabel(StringValues.LABEL_PASSWORD),
                    password
            };

            int result = JOptionPane.showConfirmDialog(view.getMainFrame(),
                    inputs,
                    StringValues.TITLE_LOGIN,
                    JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {

                /* when we log in with an account, search if there is that account in DB*/
                if (users.findUser(username.getText().trim(), String.valueOf(password.getPassword()).trim())) {

                    JOptionPane.showMessageDialog(view.getMainFrame(), StringValues.MESSAGE_SUCCESSFUL_LOGIN);

                    /* then, check if that account is normal user or admin user to show proper GUI*/
                    if ((username.getText().trim().equals(StringValues.ADMIN_USERNAME_1) &&
                            String.valueOf(password.getPassword()).trim().equals(StringValues.ADMIN_PASSWORD_1)) ||
                            (username.getText().trim().equals(StringValues.ADMIN_USERNAME_2) &&
                                    String.valueOf(password.getPassword()).trim().equals(StringValues.ADMIN_PASSWORD_2))) {

                        view.setSplitPaneBottomComponent(adminGUI.getJPanel());

                    } else {

                        view.setSplitPaneBottomComponent(userGUI.getJPanel());
                        Utils.loggedUser = users.getUser(username.getText().trim(), String.valueOf(password.getPassword()).trim());
                        userGUI.setMoneyTextField(Utils.loggedUser.getMoney());
                    }
                } else {
                    //else, if there is no record of this account, alert the user
                    JOptionPane.showMessageDialog(view.getMainFrame(), StringValues.MESSAGE_ERROR_LOGIN);
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
            JTextField money = new JTextField();

            final JComponent[] inputs = new JComponent[]{
                    new JLabel(StringValues.LABEL_USERNAME),
                    username,
                    new JLabel(StringValues.LABEL_PASSWORD),
                    password,
                    new JLabel(StringValues.LABEL_MONEY),
                    money
            };

            int result = JOptionPane.showConfirmDialog(view.getMainFrame(),
                                                        inputs,
                                                        StringValues.TITLE_REGISTER,
                                                        JOptionPane.DEFAULT_OPTION);


            /* if user presses YES, it means that he wants to create an account*/
            if (result == JOptionPane.YES_OPTION) {
                try {

                    int moneyAsInt = Integer.parseInt(money.getText().trim());
                    String passwordAsString = String.valueOf(password.getPassword()).trim();
                    String usernameAsString = username.getText().trim();

                    /* so, we check if the account (username + password) already exists in DB*/
                    if (users.findUser(usernameAsString)) {

                        JOptionPane.showMessageDialog(view.getMainFrame(), StringValues.MESSAGE_USER_EXISTS);

                    } else if (moneyAsInt >= 0 && !passwordAsString.equals("") && !usernameAsString.equals("")) {

                        /* If there if no matching user already, create the new account and notify the user
                         * that the account has been created*/
                        JOptionPane.showMessageDialog(view.getMainFrame(), StringValues.MESSAGE_SUCCESSFUL_REGISTER);
                        Users.addUser(usernameAsString, passwordAsString, moneyAsInt);

                    } else {
                        JOptionPane.showMessageDialog(view.getMainFrame(), StringValues.MESSAGE_ERROR_REGISTER);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view.getMainFrame(), StringValues.MESSAGE_ERROR_REGISTER);
                }
            }
        }
    }

    class TableItemMouseAdapter extends MouseAdapter {
        /* when we click on an item on products table, save
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
        view.setSplitPaneBottomComponent(userGUI.getJPanel());
    }

    public void setAdminGUIToView() {
        view.setSplitPaneBottomComponent(adminGUI.getJPanel());
    }

    public ProductsJTable getView() {
        return view;
    }
}
