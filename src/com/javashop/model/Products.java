package com.javashop.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class Products {

    private static ArrayList<Product> products = new ArrayList<>(); /* actual list of products from DB*/

    private static Products instance = null;    /* setup for making the class singleton*/
    /* the database url and username + password for connecting to DB*/
    private static final String DATABASE_URL = "jdbc:mysql://db4free.net:3306/shopdb2019";
    private static final String username = "proiectjava2019";
    private static final String password = "proiectjava2019";

    private static Connection connection;

    public static Products getInstance() {

        if (instance == null) { /* we make the Products class singleton because there is only one instance of all the products*/
            instance = new Products();
            fetchProductsFromDatabase();
        }
        return instance;
    }

    /* here, we get all the products from DB and save them in products ArrayList*/
    private static void fetchProductsFromDatabase() {

        try {
            connection = DriverManager.getConnection(DATABASE_URL,
                    username,
                    password);

            Statement statement = connection.createStatement();
            try (ResultSet resultSetSize = statement.executeQuery("select count(*) as size from products")) {
                resultSetSize.first();  /* move the cursor in DB at the first row because there may be more calls to this method*/
            }

            ResultSet resultSet = statement.executeQuery("select * from products");

            while (resultSet.next()) {  /*while there is a next product in DB
                                          save the product with its fields in the products ArrayList*/

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                Product product = new Product(id, name, price, quantity);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Product> getAllProducts() {
        return products;
    }

    public Product getProductAt(int index) {
        return products.get(index);
    }

    public static void removeProductsFromDB(Map<Product, Integer> productAndQuantity) {
        /* for each product in the Map parameter, we update the DB by removing that product using prepared statements*/
        try {
            for (Map.Entry<Product, Integer> productIntegerEntry : productAndQuantity.entrySet()) {

                String query = "update products set quantity = ? where id = ?";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, productIntegerEntry.getKey().getQuantity() - productIntegerEntry.getValue());
                preparedStmt.setInt(2, productIntegerEntry.getKey().getId());
                preparedStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshProducts();
    }

    public static Product getProductAtIndex(int index) {
        int i = -1;
        for (Product product : products) {
            i++;
            if (i == index) {
                return product;
            }
        }
        return null;
    }

    public static void removeProductFromDB(Product product) {
        try {
            String query = "delete from products where id = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, product.getId());
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshProducts();
    }

    public static void addProductToDB(Product product) {

        try {
            String query = "insert into products(id, name, price, quantity) values (?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshProducts();
    }

    private static void refreshProducts() { /*There may be updates to DB so we refresh the products by clearing them all
                                            from ArrayList and read again from DB.*/
        products.clear();
        fetchProductsFromDatabase();
    }
}
