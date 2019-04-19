package controller;

import data.Products;
import views.ProductsJTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductsController {

    private ProductsJTable view;
    private Products products;

    public ProductsController(ProductsJTable view, Products products){
        this.view = view;
        this.products = products;

        this.view.setLoginButtonActionListener(new LoginButtonActionListener());
    }

    class LoginButtonActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            JTextField firstName = new JTextField();
            JTextField lastName = new JTextField();
            JPasswordField password = new JPasswordField();
            final JComponent[] inputs = new JComponent[] {
                    new JLabel("First"),
                    firstName,
                    new JLabel("Last"),
                    lastName,
                    new JLabel("Password"),
                    password
            };
            int result = JOptionPane.showConfirmDialog(view.getMainFrame(),
                                                        inputs,
                                                    "My custom dialog",
                                                        JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.out.println("You entered " +
                        firstName.getText() + ", " +
                        lastName.getText() + ", " +
                        password.getText());
            } else {
                System.out.println("User canceled / closed the dialog, result = " + result);
            }
        }
    }

}
