package com.javashop.model;

import com.javashop.StringValues;
import com.javashop.Utils;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

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

    private static void fetchUsersFromDB() throws SQLException {
        /* here, we connect to DB users table and get all the users and add them to the ArrayList*/
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users");

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String userName = resultSet.getString("username");
            String userPassword = resultSet.getString("password");
            Integer userMoney = Integer.parseInt(resultSet.getString("money"));

            users.add(new User(id, userName, userPassword, userMoney));
        }
    }

    public void addUser(String username, String password, Integer money) {
        /*when register button is used and we create a new account, save it in DB by using prepared statements
         * also, save that user in users ArrayList*/

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

        int id = this.getUserIdFromDB(username,password);

        User user = new User(username, password, money);
        user.setId(id);
        users.add(user);
    }

    public void updateUserMoneyInDB(User updatedUser) {

        PreparedStatement preparedStatement;

        try {

            preparedStatement = connection.prepareStatement(" update users set money = ? where id = ?");
            preparedStatement.setInt(1, updatedUser.getMoney());
            preparedStatement.setInt(2, updatedUser.getId());
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Transaction> getUserTransactions(String username) {

        User user = getUserByUsername(username);

        PreparedStatement preparedStatement;
        ArrayList<Transaction> transactions = null;

        try {
            transactions = new ArrayList<>();
            preparedStatement = connection.prepareStatement("select * from transactions where userId = ?");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String product = resultSet.getString("product");
                int quantity = resultSet.getInt("quantity");
                String date = resultSet.getString("date");
                int userId = resultSet.getInt("userId");

                Transaction transaction = new Transaction(id, product, quantity, date, userId);

                transactions.add(transaction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public void updateTransactionInDB(Transaction transaction) {

        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into transactions(product,quantity,userId,date) values (?,?,?,?)")) {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime localDate = LocalDateTime.now();

            preparedStatement.setString(1, transaction.getProduct());
            preparedStatement.setInt(2, transaction.getQuantity());
            preparedStatement.setInt(3, transaction.getUserId());
            preparedStatement.setString(4, dtf.format(localDate));
            System.out.println(dtf.format(localDate));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Transaction> saveTransactionOfUser(User user) {
        ArrayList<Transaction> userTransactions = new ArrayList<>();

        for (Map.Entry<Product, Integer> productIntegerEntry : user.getShoppingCart().entrySet()) {
            String productName = productIntegerEntry.getKey().getName();
            int quantity = productIntegerEntry.getValue();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime localDate = LocalDateTime.now();
            int userId = user.getId();
            Transaction transaction = new Transaction(productName, quantity, dtf.format(localDate), userId);
            userTransactions.add(transaction);
        }

        return userTransactions;

    }

    public static ArrayList<User> getUsers() {
        return users;
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

    public void addProductToShoppingCart(Product product, User user) {
        /* add a specific product in a shopping cart from a specific user (mostly, logged user)*/
        user.addToShoppingCart(product);
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


    public boolean findUser(String username) {
        /* here we check if a user with specified username exists in DB*/
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
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

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    public int getUserIdFromDB(String username, String password){

        try (PreparedStatement preparedStatement = connection.prepareStatement("select id from users where username = ? and password = ? ")) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return StringValues.ERROR_DATABASE;
    }

}
