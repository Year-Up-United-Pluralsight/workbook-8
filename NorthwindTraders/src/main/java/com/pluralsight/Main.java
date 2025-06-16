package com.pluralsight;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

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

        // Establish variables with null outside try block
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            // Loads the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (
                // 1. Opens Connection to Database (uses the database URL to point to the correct database)
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/northwind", username, password);
            )
            {


                try (
                    PreparedStatement statement1;
                    ResultSet resultSet1 = statement1.execute();

                )
                {
                    // while
                }

                System.out.print("Please enter an employee name to search by ➤ ");

                try (
                    PreparedStatement statement2;
                )
                {
                    // set parameter on statement

                    try (
                        Resultset resultSet = statement2.executeQuery();
                    )
                    {
                        // while
                    }
                }

            }

            catch (Exception e) {
                e.printStackTrace();
            }

            catch (SQLException e) {
                e.printStackTrace();
            }

            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (userChoice == 1) {

                // 2. Create preparedStatement for query
                preparedStatement = connection.prepareStatement(
                        "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products"
                );

                // 3. Execute your query
                resultSet = preparedStatement.executeQuery();

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

            } else if (userChoice == 2) {

                // 2. Create preparedStatement for query
                preparedStatement = connection.prepareStatement(
                        "SELECT ContactName, CompanyName, City, Country, Phone " +
                                "FROM Customers " +
                                "ORDER BY Country"
                );

                // 3. Execute your query
                resultSet = preparedStatement.executeQuery();

                // Print header
                System.out.printf("%-27s %-35s %-21s %-14s %-10s%n", "ContactName", "CompanyName", "City", "Country", "Phone");
                System.out.println("-------------------------------------------------------------------------------------------------------------------------");

                // Loops through the results
                while (resultSet.next()) {

                    String contactName = resultSet.getString("ContactName");
                    String companyName = resultSet.getString("CompanyName");
                    String city = resultSet.getString("City");
                    String country = resultSet.getString("Country");
                    String phone = resultSet.getString("Phone");

                    System.out.printf("%-27s %-35s %-21s %-14s %-10s%n", contactName, companyName, city, country, phone);
                }
            }

            else if (userChoice == 3) {

                // 2. Create preparedStatement for query
                preparedStatement = connection.prepareStatement(
                        "SELECT CategoryID, CategoryName " +
                                "FROM Categories " +
                                "ORDER BY CategoryID"
                );

                System.out.print("Which category ID would you like to view? ➤ ");
                int userIDinput = scanner.nextInt();
                scanner.nextLine();

                // 2. Again, create preparedStatement for another query
                preparedStatement = connection.prepareStatement(
                        "SELECT ProductID, ProductName, UnitPrice, UnitsInStock " +
                                "FROM Products " +
                                "WHERE CategoryID = ?"
                );

                preparedStatement.setString(1, userIDinput);

                // 3. Execute your query
                resultSet = preparedStatement.executeQuery();

                // Print header
                System.out.printf("%-27s %-35s %-21s %-14s%n", "ProductID", "ProductName", "UnitPrice", "UnitsInStock");
                System.out.println("-------------------------------------------------------------------------------------------------------------------------");

                // Loops through the results
                while (resultSet.next()) {

                    String productID = resultSet.getString("ProductID");
                    String productName = resultSet.getString("ProductName");
                    double unitPrice = resultSet.getDouble("UnitPrice");
                    scanner.nextLine();
                    int unitsInStock = resultSet.getInt("UnitsInStock");
                    scanner.nextLine();

                    System.out.printf("%-27s %-35s %-14d %-10f%n", productID, productName, unitPrice, unitsInStock);
                }
            }

            else {
                return;
            }

        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            // 3. Closes the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}