package com.javashop.data;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class Users {

    // actual list of users
    private static ArrayList<User> users = new ArrayList<>();

    private static Users instance = null;

    private static Connection connection;

    private Users() {
        final String DATABASE_URL = "jdbc:mysql://db4free.net:3306/shopdb2019";
        final String username = "proiectjava2019";
        final String password = "proiectjava2019";

        try {
            connection = DriverManager.getConnection(DATABASE_URL,
                    username,
                    password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Users getInstance() {

        if (instance == null) {

            instance = new Users();

            try {
                fetchUsersFromDB();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    private static void fetchUsersFromDB() throws SQLException {


        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users");

        while (resultSet.next()) {
            String userName = resultSet.getString("username");
            String userPassword = resultSet.getString("password");

            //addUser(userName, userPassword);
            users.add(new User(userName,userPassword));
        }

    }



    public boolean findUser(String username, String password) {

        for (User user : users) {
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void printAllUsers() {
        for (User user : users) {
            System.out.println(user);
        }
    }

    public static void addUser(String username, String password) {
        User user = new User(username, password);
        users.add(user);

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("insert into users(username,password) VALUES(?,?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
