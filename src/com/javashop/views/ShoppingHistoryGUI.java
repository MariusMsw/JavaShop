package com.javashop.views;

import com.javashop.model.User;
import com.javashop.model.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShoppingHistoryGUI {

    private JList<Object> usersList;
    private JSplitPane splitPane;
    private JPanel bottomPanel = new JPanel();

    private JButton showTransactionsButton = new JButton("Show Transactions");
    private JButton backButton = new JButton("Back");

    public ShoppingHistoryGUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        splitPane = ProductsJTable.getSplitPane();
        String[] usersNames = getUsersNames();
        usersList = new JList<>(usersNames);
        usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usersList.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel.add(showTransactionsButton);
        bottomPanel.add(backButton);

        splitPane.setTopComponent(usersList);
        splitPane.setBottomComponent(bottomPanel);
        splitPane.setDividerLocation(((int) screenSize.getHeight() / 2) * 76 / 100);
    }

    private String[] getUsersNames() {
        ArrayList<User> users = Users.getUsers();

        ArrayList<String> aux = new ArrayList<>();
        int i = 0;
        for (User user : users) {
            aux.add(user.getUsername());
            i++;
        }

        String[] usersNames = new String[i];
        i = 0;
        for (String name : aux) {
            usersNames[i] = name;
            i++;
        }

        return usersNames;
    }

    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void setShowTransactionsButtonListener(ActionListener listener) {
        showTransactionsButton.addActionListener(listener);
    }

    public JList<Object> getUsersList() {
        return usersList;
    }
}
