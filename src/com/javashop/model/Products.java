package com.javashop.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

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

    public static Product getProductAt(int index) {
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

                updateQuantityOfProductInProducts(productIntegerEntry.getKey(), productIntegerEntry.getKey().getQuantity() - productIntegerEntry.getValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateQuantityOfProductInProducts(Product product, Integer quantity) {
        Objects.requireNonNull(findProduct(product.getId())).setQuantity(quantity);
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

        products.remove(product);
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
            products.add(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modifyProductInDB(Product product) {

        String EMPTY_STRING = "";

        try {

            String queryUpdateName = "update products set name = ? where id = ?";
            String queryUpdatePrice = "update products set price = ? where id = ?";
            String queryUpdateQuantity = "update products set quantity = ? where id = ?";
            String queryUpdateNamePrice = "update products set name = ?, price = ? where id = ?";
            String queryUpdateNameQuantity = "update products set name = ?,quantity = ? where id = ?";
            String queryUpdatePriceQuantity = "update products set price = ?, quantity = ? where id = ?";
            String queryUpdateProduct = "update products set name = ?, price = ?, quantity = ? where id = ?";

            // Modify the name
            if (!product.getName().equals(EMPTY_STRING) && product.getPrice() == 0d && product.getQuantity() == 0) {
                PreparedStatement preparedStatement = connection.prepareStatement(queryUpdateName);
                preparedStatement.setString(1, product.getName());
                preparedStatement.setInt(2, product.getId());

                preparedStatement.executeUpdate();

                Product updatedProduct = findProduct(product.getId());

                if (updatedProduct != null) {
                    updatedProduct.setName(product.getName());
                }
            }

            // Modify the price
            if (product.getName().equals(EMPTY_STRING) && product.getPrice() != 0d && product.getQuantity() == 0) {
                PreparedStatement preparedStatement = connection.prepareStatement(queryUpdatePrice);
                preparedStatement.setDouble(1, product.getPrice());
                preparedStatement.setInt(2, product.getId());

                preparedStatement.executeUpdate();

                Product updatedProduct = findProduct(product.getId());

                if (updatedProduct != null) {
                    updatedProduct.setPrice(product.getPrice());
                }
            }

            // Modify the quantity
            if (product.getName().equals(EMPTY_STRING) && product.getPrice() == 0d && product.getQuantity() != 0) {
                PreparedStatement preparedStatement = connection.prepareStatement(queryUpdateQuantity);
                preparedStatement.setInt(1, product.getQuantity());
                preparedStatement.setInt(2, product.getId());

                preparedStatement.executeUpdate();

                Product updatedProduct = findProduct(product.getId());

                if (updatedProduct != null) {
                    updatedProduct.setQuantity(product.getQuantity());
                }
            }

            // Modify the name and the price
            if (!product.getName().equals(EMPTY_STRING) && product.getPrice() != 0d && product.getQuantity() == 0) {
                PreparedStatement preparedStatement = connection.prepareStatement(queryUpdateNamePrice);
                preparedStatement.setString(1, product.getName());
                preparedStatement.setDouble(2, product.getPrice());
                preparedStatement.setInt(3, product.getId());

                preparedStatement.executeUpdate();

                Product updatedProduct = findProduct(product.getId());

                if (updatedProduct != null) {
                    updatedProduct.setName(product.getName());
                    updatedProduct.setPrice(product.getPrice());
                }
            }

            // Modify the name and the quantity
            if (!product.getName().equals(EMPTY_STRING) && product.getPrice() == 0d && product.getQuantity() != 0) {
                PreparedStatement preparedStatement = connection.prepareStatement(queryUpdateNameQuantity);
                preparedStatement.setString(1, product.getName());
                preparedStatement.setInt(2, product.getQuantity());
                preparedStatement.setInt(3, product.getId());

                preparedStatement.executeUpdate();

                Product updatedProduct = findProduct(product.getId());

                if (updatedProduct != null) {
                    updatedProduct.setName(product.getName());
                    updatedProduct.setQuantity(product.getQuantity());
                }
            }

            // Modify the price and the quantity
            if (product.getName().equals(EMPTY_STRING) && product.getPrice() != 0d && product.getQuantity() != 0) {
                PreparedStatement preparedStatement = connection.prepareStatement(queryUpdatePriceQuantity);
                preparedStatement.setDouble(1, product.getPrice());
                preparedStatement.setInt(2, product.getQuantity());
                preparedStatement.setInt(3, product.getId());

                preparedStatement.executeUpdate();

                Product updatedProduct = findProduct(product.getId());

                if (updatedProduct != null) {
                    updatedProduct.setPrice(product.getPrice());
                    updatedProduct.setQuantity(product.getQuantity());
                }
            }

            // Modify the product
            if (!product.getName().equals(EMPTY_STRING) && product.getPrice() != 0d && product.getQuantity() != 0) {
                PreparedStatement preparedStatement = connection.prepareStatement(queryUpdateProduct);
                preparedStatement.setString(1, product.getName());
                preparedStatement.setDouble(2, product.getPrice());
                preparedStatement.setInt(3, product.getQuantity());
                preparedStatement.setInt(4, product.getId());

                preparedStatement.executeUpdate();

                Product updatedProduct = findProduct(product.getId());

                if (updatedProduct != null) {
                    updatedProduct.setName(product.getName());
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Integer calculateCapital() {

        int capital = 0;

        for (Product product : products) {
            capital += product.getPrice() * product.getQuantity();
        }

        return capital;
    }

    private static Product findProduct(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }


}
