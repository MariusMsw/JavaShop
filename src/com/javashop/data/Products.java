package data;

import java.sql.*;
import java.util.ArrayList;

public class Products {

    // actual list of products
    private static ArrayList<Product> products = new ArrayList<>();


    // setup for making the class singleton
    private static Products instance = null;

    private Products(){ }

    public static Products getInstance(){

        if(instance == null){

            instance = new Products();
            fetchProductsFromDatabase();
        }

        return instance;

    }

    // get data from database server
    private static void fetchProductsFromDatabase(){
        final String DATABASE_URL = "jdbc:mysql://db4free.net:3306/shopdb2019";
        final String username = "proiectjava2019";
        final String password = "proiectjava2019";



        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL,
                                            username,
                                        password);

            Statement statement = connection.createStatement();
            ResultSet resultSetSize = statement.executeQuery("select count(*) as size from products");

            resultSetSize.first();
            int resultSize = resultSetSize.getInt("size");

            resultSetSize.close();

            ResultSet resultSet = statement.executeQuery("select * from products");

            while (resultSet.next()) {


                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                Product product = new Product(name, price, quantity);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    // Utilities methods for working with data
    public void printAllProducts(){
        for(Product product : products){
            System.out.println(product);
        }
    }

    public static ArrayList<Product> getAllProducts(){
        return products;
    }

}
