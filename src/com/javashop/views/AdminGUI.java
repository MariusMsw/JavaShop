package com.javashop.views;

import javax.swing.*;
import java.awt.*;

public class AdminGUI {

    private JPanel adminPanel;
    private JButton addProductInDataBaseButton = new JButton("Add product");
    private JButton removeProductFromDataBaseButton = new JButton("Remove product");
    private JButton modifyProductButton = new JButton("Modify product");
    private JButton logoutButton = new JButton("Logout");

    public AdminGUI() {

        adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(1, 4));
        adminPanel.add(addProductInDataBaseButton);
        adminPanel.add(removeProductFromDataBaseButton);
        adminPanel.add(modifyProductButton);
        adminPanel.add(logoutButton);
    }

    public JPanel getJPanel() {

        return adminPanel;
    }
}
