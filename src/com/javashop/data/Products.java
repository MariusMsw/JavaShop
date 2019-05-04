package com.javashop.data;

import com.javashop.data.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class Products {

    // actual list of products
    private static ArrayList<Product> products = new ArrayList<>();


    // setup for making the class singleton
    private static Products instance = null;


    static final String DATABASE_URL = "jdbc:mysql://db4free.net:3306/shopdb2019";
    static final String username = "proiectjava2019";
    static final String password = "proiectjava2019";

    private static Connection connection;


    private Products() {
    }

    public static Products getInstance() {

        if (instance == null) {

            instance = new Products();
            fetchProductsFromDatabase();
        }

        return instance;

    }

    // get data from database server
    private static void fetchProductsFromDatabase() {


        try {
            connection = DriverManager.getConnection(DATABASE_URL,
                    username,
                    password);

            Statement statement = connection.createStatement();
            ResultSet resultSetSize = statement.executeQuery("select count(*) as size from products");

            resultSetSize.first();
            int resultSize = resultSetSize.getInt("size");

            resultSetSize.close();

            ResultSet resultSet = statement.executeQuery("select * from products");

            while (resultSet.next()) {


                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                Product product = new Product(id,name, price, quantity);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Utilities methods for working with data
    public void printAllProducts() {
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public static ArrayList<Product> getAllProducts() {
        return products;
    }

    public Product getProductAt(int index) {
        return products.get(index);
    }

    public static void removeProductFromDB(Map<Product,Integer> productAndQuantity){

        System.out.println("removeProductFromDB()" + productAndQuantity.size());

        try {

            for(Map.Entry<Product,Integer> productIntegerEntry : productAndQuantity.entrySet()){

                String query = "update products set quantity = ? where id = ?";

                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, productIntegerEntry.getKey().getQuantity() - productIntegerEntry.getValue() );
                preparedStmt.setInt(2, productIntegerEntry.getKey().getId());
                preparedStmt.executeUpdate();

                System.out.println( " id=" + productIntegerEntry.getKey().getId() + " name=" +
                                        productIntegerEntry.getKey().getName());
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshProducts();

    }

    private static void refreshProducts(){
        products.clear();
        fetchProductsFromDatabase();
    }

}
