package com.javashop.controller;

import com.javashop.data.*;
import com.javashop.views.AdminGUI;
import com.javashop.views.ProductsJTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class ProductsController {

    private ProductsJTable view;
    private Products products;
    private Users users;

    public ProductsController(ProductsJTable view, Products products) {
        this.view = view;
        this.products = products;

        this.view.setLoginButtonActionListener(new LoginButtonActionListener());
        this.view.setRegisterButtonActionListener(new RegisterButtonActionListener());

        this.view.setProductsForJTable(convertProductsToData());
        users = Users.getInstance();
    }

    private String[][] convertProductsToData() {

        ArrayList<Product> theProducts = Products.getAllProducts();

        String[][] data = null;
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

        System.out.println("in controller");

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
                        password.getText());

                if (users.findUser(username.getText().trim(), password.getText().trim())) {
                    System.out.println("Exista!");
                    view.setContent(new AdminGUI().getJPanel());

                } else {
                    System.out.println("Nu exista!");
                }
            } else {
                System.out.println("User canceled / closed the dialog, result = " + result);
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
            if(result == JOptionPane.YES_OPTION) {

                //
                // If user exists in DB, then alert him/her
                //
                if (users.findUser(username.getText().trim(), password.getText().trim())) {
                    System.out.println(" User exista! (register)");
                } else {

                    // If there is no user them add him/her
                    //
                    Users.addUser(username.getText().trim(), password.getText().trim());
                }
            }else {

                // User canceled the dialog
                System.out.println("User canceled the dialog");
            }
        }
    }
}
