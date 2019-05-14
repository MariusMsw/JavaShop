package com.javashop.model;

import java.sql.*;
import java.util.ArrayList;

public class Users {

    /* actual list of users */
    private static ArrayList<User> users = new ArrayList<>();

    private static Users instance = null; /* we make Users class singleton because there is only one instance of all users needed*/

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
        /* here, we connect to DB users table and get all the users and add them to the ArrayList*/
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users");

        while (resultSet.next()) {
            String userName = resultSet.getString("username");
            String userPassword = resultSet.getString("password");
            Integer userMoney = Integer.parseInt(resultSet.getString("money"));

            users.add(new User(userName, userPassword, userMoney));
        }
    }

    public boolean findUser(String username, String password) {
        /* here we check if a user with specified username and password exists in DB*/
        for (User user : users) {
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public User getUser(String username, String password) {
        /* we return the user with a specified username and password from DB but, if there is no instance of that user, we return null*/
        for (User user : users) {
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static void addUser(String username, String password, Integer money) {
        /*when register button is used and we create a new account, save it in DB by using prepared statements
         * also, save that user in users ArrayList*/
        User user = new User(username, password, money);
        users.add(user);

        PreparedStatement statement;

        try {
            statement = connection.prepareStatement("insert into users(username,password, money) VALUES(?,?,?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, money);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProductToShoppingCart(Product product, User user) {
        /* add a specific product in a shopping cart from a specific user (mostly, logged user)*/
        user.addToShoppingCart(product);
    }
}
