package com.javashop.views;

import com.javashop.StringValues;
import com.javashop.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserGUI {

    private JPanel userPanel;
    private JButton addToShoppingCartButton = new JButton(StringValues.BUTTON_ADD_TO_CART);
    private JButton showShoppingCartButton = new JButton(StringValues.BUTTON_SHOW_CART);
    private JButton checkoutButton = new JButton(StringValues.BUTTON_CHECKOUT);
    private JButton logoutButton = new JButton(StringValues.BUTTON_LOGOUT);
    private JTextField moneyTextField = new JTextField(StringValues.TEXT_MONEY);

    public UserGUI() {
        userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(StringValues.USER_GUI_ROWS, StringValues.USER_GUI_COLUMNS));

        userPanel.add(addToShoppingCartButton);
        userPanel.add(showShoppingCartButton);
        userPanel.add(checkoutButton);
        userPanel.add(logoutButton);
        userPanel.add(moneyTextField);

        Utils.productSelected = StringValues.NO_PRODUCT_SELECTED;
        moneyTextField.setHorizontalAlignment(SwingConstants.CENTER);
        moneyTextField.setEditable(false);
        moneyTextField.setFont(moneyTextField.getFont().deriveFont(Font.BOLD));
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
        moneyTextField.setText(StringValues.TEXT_MONEY+ integer);
    }
}
