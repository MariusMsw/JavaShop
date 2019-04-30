package com.javashop.data;

import java.sql.*;
import java.util.ArrayList;

public class Users {

    // actual list of users
    private static ArrayList<User> users = new ArrayList<>();

    private static Users instance = null;

    private Users(){}

    public static Users getInstance(){

        if(instance == null){
            instance = new Users();
            fetchUsersFromDB();
        }

        return instance;
    }

    private static void fetchUsersFromDB(){

        final String DATABASE_URL = "jdbc:mysql://db4free.net:3306/shopdb2019";
        final String username = "proiectjava2019";
        final String password = "proiectjava2019";


        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL,
                    username,
                    password);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");

            while (resultSet.next()) {


                String userName = resultSet.getString("username");
                String userPassword = resultSet.getString("password");

                User newUser = new User(userName, userPassword);
                users.add(newUser);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean findUser(String username, String password){

        for(User user : users){
            if(user.getUsername().equals(username) &&
                user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public void printAllUsers(){
        for(User user : users){
            System.out.println(user);
        }
    }

}
