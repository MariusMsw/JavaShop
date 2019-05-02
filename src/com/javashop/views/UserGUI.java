package com.javashop.views;

import javax.swing.*;
import java.awt.*;

public class UserGUI {
    private JPanel userPanel;

    public UserGUI() {
        userPanel = new JPanel();
        JTextField textField = new JTextField("asfasfrtgsdgsdgsdas");
        userPanel.add(textField);
    }

    public JPanel getJPanel() {
        return userPanel;
    }
}
