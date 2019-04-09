import data.Product;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ProductsJTable {

    JFrame frame;

    JTable table;

    Product[] products = null;

    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private static final String DATABASE_URL = "jdbc:mysql://db4free.net:3306/shopdb2019";
    private static final String username = "proiectjava2019";
    private static final String password = "proiectjava2019";


    private static Connection createDatabaseConnection() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL,
                    username,
                    password);

            /*
            Statement statement = mConnection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from products");

            while (resultSet.next()){
                System.out.println(resultSet.getString("price"));
            }
            */
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    private Product[] getAllProducts() {

        Connection connection = createDatabaseConnection();

        try {

            Statement statement = connection.createStatement();
            ResultSet resultSetSize = statement.executeQuery("select count(*) as size from products");

            resultSetSize.first();
            int resultSize = resultSetSize.getInt("size");

//            System.out.println("am trecut pe aici" + resultSize);
            resultSetSize.close();

            products = new Product[resultSize];

            ResultSet resultSet = statement.executeQuery("select * from products");

            int index = 0;
            while (resultSet.next()) {


                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                Product product = new Product(name, price, quantity);
                products[index] = product;
                index++;
            }

            return products;

        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }

    }

    public ProductsJTable() {

        frame = new JFrame("Shop");
        frame.setSize((int) screenSize.getWidth() / 2, (int) screenSize.getHeight() / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        products = getAllProducts();

        String[][] data = new String[products.length][3];

        int index = 0;
        for (Product product : products) {
            data[index][0] = product.getName();
            data[index][1] = String.valueOf(product.getPrice());
            data[index][2] = String.valueOf(product.getQuantity());

            //       System.out.println(data[index][0] + " " + data[index][1] + " " + data[index][2]);

            index++;
        }

        String[] columnNames = {"Name", "Price", "Stock"};

        table = new JTable(data, columnNames);
        table.setBounds(30, 40, 200, 300);

        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(scrollPane);
        frame.setVisible(true);


    }


}
