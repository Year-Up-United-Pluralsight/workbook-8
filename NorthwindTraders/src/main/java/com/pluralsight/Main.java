package com.pluralsight;

import java.sql.Connection;
import java.sql.*;

public class Main {
    public static void main(String[] args) {

        try {

            // load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 1. open a connection to the database
            // use the database URL to point to the correct database
            Connection connection;
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    "root",
                    "Dingreen1108");

            // create statement
            // the statement is tied to the open connection
            Statement statement = connection.createStatement();

            // define your query
            String query = "SELECT * FROM Products";

            // 2. Execute your query
            ResultSet results = statement.executeQuery(query);

            // process the results
            while (results.next()) {
                String product = results.getString("ProductName");
                System.out.println(product);
            }

            // 3. Close the connection
            connection.close();
        }

        catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
}