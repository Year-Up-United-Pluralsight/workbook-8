package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Gets user choice
        System.out.print("""
                What do you want to do?
                   1) Display all products
                   2) Display all customers
                   3) Display all categories
                   0) Exit
                Select an option ➤\s""");

        Scanner scanner = new Scanner(System.in);
        int userChoice = scanner.nextInt();
        scanner.nextLine();

        System.out.println();

        // Ensures there is username and password
        if (args.length != 2) {
            System.out.print(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.UsingDriverManager <username> <password>");
            System.exit(1);
        }

        // Gets username and password from command line args
        String username = args[0];
        String password = args[1];

        // Creates the datasource
        BasicDataSource dataSource = new BasicDataSource();

        // Configures the datasource
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // Establish variables with null outside try block
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        // Uses dataSource to get connection
        try (
            // 1. Opens Connection to Database
            Connection connection = dataSource.getConnection();
        )
        {
            if (userChoice == 1) {

                try (
                    // 2. Create preparedStatement for query
                    PreparedStatement preparedStatement1 = connection.prepareStatement(
                            "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products"
                    );

                    // 3. Execute your query
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                )
                {
                    // Print header
                    System.out.printf("%-11s %-35s %-11s %-14s%n", "ProductID", "ProductName", "UnitPrice", "UnitsInStock");
                    System.out.println("---------   ---------------------------------   ----------  ----------------");

                    // Loops through the results
                    while (resultSet1.next()) {

                        int productId = resultSet1.getInt("ProductID");
                        String productName = resultSet1.getString("ProductName");
                        double unitPrice = resultSet1.getDouble("UnitPrice");
                        int unitsInStock = resultSet1.getInt("UnitsInStock");

                        System.out.printf("%-11s %-35s %-11s %-14s%n", productId, productName, unitPrice, unitsInStock);
                    }
                }
            }

            else if (userChoice == 2) {

                try (
                    // 2. Create preparedStatement for query
                    PreparedStatement preparedStatement2 = connection.prepareStatement(
                            "SELECT ContactName, CompanyName, City, Country, Phone " +
                                    "FROM Customers " +
                                    "ORDER BY Country"
                    );

                    // 3. Execute your query
                    ResultSet resultSet2 = preparedStatement2.executeQuery();
                )
                {
                    // Print header
                    System.out.printf("%-27s %-35s %-21s %-14s %-10s%n", "ContactName", "CompanyName", "City", "Country", "Phone");
                    System.out.println("-------------------------------------------------------------------------------------------------------------------------");

                    // Loops through the results
                    while (resultSet2.next()) {

                        String contactName = resultSet2.getString("ContactName");
                        String companyName = resultSet2.getString("CompanyName");
                        String city = resultSet2.getString("City");
                        String country = resultSet2.getString("Country");
                        String phone = resultSet2.getString("Phone");

                        System.out.printf("%-27s %-35s %-21s %-14s %-10s%n", contactName, companyName, city, country, phone);
                    }
                }
            }

            else if (userChoice == 3) {
                // DISPLAYS TO USER ALL CATEGORIES BY ID AND NAME
                try (

                        // Creates preparedStatement for query
                        PreparedStatement preparedStatement3a = connection.prepareStatement(
                                "SELECT CategoryID, CategoryName " +
                                        "FROM Categories " +
                                        "ORDER BY CategoryID");

                        // Gets result set of the query
                        ResultSet resultSet3a = preparedStatement3a.executeQuery();
                ) {
                    // Print header
                    System.out.printf("%-27s %-35s%n", "Category ID", "Category Name");
                    System.out.println("-------------------------------------------------------------------------------------------------------------------------");

                    // Loops through the results
                    while (resultSet3a.next()) {

                        String categoryID = resultSet3a.getString("CategoryID");
                        String categoryName = resultSet3a.getString("CategoryName");

                        System.out.printf("%-27s %-35s%n", categoryID, categoryName);
                    }
                }

                // PROMPTS USER FOR CATEGORY ID
                System.out.println();
                System.out.print("Please enter a category ID to filter products by ➤ ");
                String categoryIdInput = scanner.nextLine();
                System.out.println();

                // DISPLAYS TO USER PRODUCTS IN CHOSEN CATEGORY ID
                try (
                        // Again, create preparedStatement for another query
                        PreparedStatement preparedStatement3b = connection.prepareStatement(
                                "SELECT ProductID, ProductName, UnitPrice, UnitsInStock " +
                                        "FROM Products " +
                                        "WHERE CategoryID = ?"
                        );
                ) {
                    // Set parameter onto the statement
                    preparedStatement3b.setString(1, categoryIdInput);

                    try (
                        // Executes query
                        ResultSet resultSet3b = preparedStatement3b.executeQuery();
                    ) {

                        // Print header
                        System.out.printf("%-27s %-35s %-21s %-14s%n", "ProductID", "ProductName", "UnitPrice", "UnitsInStock");
                        System.out.println("-------------------------------------------------------------------------------------------------------------------------");

                        // Loops through the results
                        while (resultSet3b.next()) {

                            String productID = resultSet3b.getString("ProductID");
                            String productName = resultSet3b.getString("ProductName");
                            double unitPrice = resultSet3b.getDouble("UnitPrice");
                            int unitsInStock = resultSet3b.getInt("UnitsInStock");

                            System.out.printf("%-27s %-35s %-14f %-10d%n", productID, productName, unitPrice, unitsInStock);
                        }
                    }
                }
            }
        }


        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}