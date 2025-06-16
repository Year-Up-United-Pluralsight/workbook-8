package com.pluralsight;

import java.sql.Connection;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Ensures there is username and password
        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.UsingDriverManager <username> <password>");
            System.exit(1);
        }

        // Gets username and password from command line args
        String username = args[0];
        String password = args[1];

        // Loads the MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 1. Opens Connection to Database (uses the database URL to point to the correct database)
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind", username, password);

        // 2. Create preparedStatement for query
        // the statement is tied to the open connection
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products"
        );

        // 3. Execute your query
        ResultSet resultSet = preparedStatement.executeQuery();

        // Print header
        System.out.printf("%-11s %-35s %-11s %-14s%n", "ProductID", "ProductName", "UnitPrice", "UnitsInStock");
        System.out.println("---------   ---------------------------------   ----------  ----------------");

        // Loops through the results
        while (resultSet.next()) {

            int productId = resultSet.getInt("ProductID");
            String productName = resultSet.getString("ProductName");
            double unitPrice = resultSet.getDouble("UnitPrice");
            int unitsInStock = resultSet.getInt("UnitsInStock");

            System.out.printf("%-11s %-35s %-11s %-14s%n", productId, productName, unitPrice, unitsInStock);
        }

        // 3. Closes the resources
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}