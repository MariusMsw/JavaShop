package com.javashop.controller;

import com.javashop.Main;
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
import java.util.HashMap;
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
        ProductsJTable.setProductsForJTable(this.view.getTable(), convertProductsToData(Products.getAllProducts()),
                                                "Name","Price","Stock");


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

        int index = 0;
        for (Product product : Objects.requireNonNull(theProducts)) {

            data[index][0] = product.getName();
            data[index][1] = String.valueOf(product.getPrice());
            data[index][2] = String.valueOf(product.getQuantity());

            index++;
        }

        //System.out.println("Program started!");

        return data;

    }

    public static String[][] convertProductsToData(Map<Product,Integer> theProducts) {

        String[][] data;
        if (theProducts != null) {
            data = new String[theProducts.size()][3];
        } else {
            data = new String[0][0];
        }

        int index = 0;

        for (Map.Entry<Product,Integer> product : theProducts.entrySet()){

            data[index][0] = product.getKey().getName();
            data[index][1] = String.valueOf(product.getKey().getPrice());
            data[index][2] = String.valueOf(product.getValue());

            index++;
        }

        //System.out.println("Program started!");

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

                if (users.findUser(username.getText().trim(), String.valueOf(password.getPassword()).trim())) {

                    JOptionPane.showMessageDialog(view.getMainFrame(), "Successful login!");

                    /// if user successful logged in, check if he is admin or user logged
                    if ((username.getText().trim().equals("marius") && String.valueOf(password.getPassword()).trim().equals("1234")) ||
                            (username.getText().trim().equals("bogdan") && String.valueOf(password.getPassword()).trim().equals("1234"))) {

                        view.setContent(adminGUI.getJPanel());

                    } else {

                        view.setContent(userGUI.getJPanel());
                        Utils.loggedUser = users.getUser(username.getText().trim(), String.valueOf(password.getPassword()).trim());

                    }
                } else {
                    JOptionPane.showMessageDialog(view.getMainFrame(), "Can't connect!\n" +
                            "Try again.");
                }
            }
        }
    }

    class RegisterButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //
            // Create Dialog
            //
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
            // If yes option was selected try to
            // connect the user
            //
            if (result == JOptionPane.YES_OPTION) {
                //
                // If user exists in DB, then alert him/her
                //
                if (users.findUser(username.getText().trim(), String.valueOf(password.getPassword()).trim())) {
                    JOptionPane.showMessageDialog(view.getMainFrame(), "User already exists!");
                } else {
                    // If there is no user them add him/her
                    JOptionPane.showMessageDialog(view.getMainFrame(), "Successful register!");
                    Users.addUser(username.getText().trim(), String.valueOf(password.getPassword()).trim());
                }
            }
        }
    }

    class TableItemMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {

            Utils.productSelected = view.getTable().rowAtPoint(e.getPoint());
            System.out.println("ai dat click pe " + view.getTable().rowAtPoint(e.getPoint()) + " id= " +
                                Products.getAllProducts().get(Utils.productSelected).getId() + " name=" +
                                Products.getAllProducts().get(Utils.productSelected).getName());

        }
    }

    public UserGUI getUserGUI() {
        return userGUI;
    }

    public AdminGUI getAdminGUI() {
        return adminGUI;
    }


    // Set proper UI(bottom panel with buttons) when back button is pressed
    public void setUserGUItoView(){
        view.setContent(userGUI.getJPanel());
    }

    public void setAdminGUIToView(){
        view.setContent(adminGUI.getJPanel());
    }

    public ProductsJTable getView(){
        return view;
    }
}
