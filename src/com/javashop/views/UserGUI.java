package com.javashop.views;

import com.javashop.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserGUI {

    private JPanel userPanel;
    private JButton addToShoppingCartButton = new JButton("Add to cart");
    private JButton showShoppingCartButton = new JButton("Show cart");
    private JButton checkoutButton = new JButton("Checkout");
    private JButton logoutButton = new JButton("Logout");
    private JTextField moneyTextField = new JTextField("Money: ");

    public UserGUI() {
        userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(1, 5));

        userPanel.add(addToShoppingCartButton);
        userPanel.add(showShoppingCartButton);
        userPanel.add(checkoutButton);
        userPanel.add(logoutButton);
        userPanel.add(moneyTextField);

        Utils.productSelected = -1;
        moneyTextField.setHorizontalAlignment(SwingConstants.CENTER);
        moneyTextField.setEditable(false);
    }

    public JPanel getJPanel() {
        return userPanel;
    }

    public void setAddToShoppingCartButtonListener(ActionListener listener) {
        this.addToShoppingCartButton.addActionListener(listener);
    }

    public void setShowShoppingCartButtonListener(ActionListener listener) {
        this.showShoppingCartButton.addActionListener(listener);
    }

    public void setCheckoutButtonListener(ActionListener listener) {
        this.checkoutButton.addActionListener(listener);
    }

    public void setLogoutButtonListener(ActionListener listener) {
        this.logoutButton.addActionListener(listener);
    }

    public void setMoneyTextField(Integer integer) {
        moneyTextField.setText("Money: " + integer);
    }
}
