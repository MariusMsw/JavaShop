package com.javashop;

import data.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ProductsJTable {

    private JFrame frame;

    private JTable table;

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
        frame.setLayout(new BorderLayout());

        products = getAllProducts();

        String[][] data = null;
        if(products != null) {
            data = new String[products.length][3];
        }else {
            data = new String[0][0];
        }

        int index = 0;
        for (Product product : products) {

            data[index][0] = product.getName();
            data[index][1] = String.valueOf(product.getPrice());
            data[index][2] = String.valueOf(product.getQuantity());

            index++;
        }

        String[] columnNames = {"Name", "Price", "Stock"};

        table = new JTable(data, columnNames);
        table.setBounds(30, 40, 200, 300);

        JScrollPane scrollPane = new JScrollPane(table);
        JButton loginButton = new JButton("LOGIN");

        frame.add(scrollPane,BorderLayout.CENTER);
        frame.add(loginButton,BorderLayout.SOUTH);
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = 0;
                for(int i = 0; i < Integer.MAX_VALUE; i++){
                    for(int j = 0; j < Integer.MAX_VALUE; j++){
                        a++;
                    }
                }
                /*
                System.out.println("a=" + a);
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                */
            }
        });



    }


}
