package com.javashop;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
/*
        try {
            //1. get connection to database
            Connection myConn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/shopdb2019", "proiectjava2019", "proiectjava2019");
            //2. create a statement
            Statement myStatement = myConn.createStatement();
            //3. execute sql query
            ResultSet myResultSet = myStatement.executeQuery("select * from products");
            //4. process the result set
            while (myResultSet.next()) {
                System.out.println(myResultSet.getString("Pret"));
            }
            //5. insert a row in table

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    
		new ProductsJTable();
	}
}
