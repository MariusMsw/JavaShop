package com.javashop.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserGUI {

    private JPanel userPanel;
    private JButton addToShoppingCartButton = new JButton("Add to cart");
    private JButton showShoppingCartButton = new JButton("Show cart");
    private JButton checkoutButton = new JButton("Checkout");
    private JButton logoutButton = new JButton("Logout");

    public UserGUI() {
        userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(1, 4));

        userPanel.add(addToShoppingCartButton);
        userPanel.add(showShoppingCartButton);
        userPanel.add(checkoutButton);
        userPanel.add(logoutButton);
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
}
