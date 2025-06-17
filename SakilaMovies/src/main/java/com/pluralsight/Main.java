package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the last name of your favorite actor ➤ ");
        String actorLastName = scanner.nextLine();

        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.hca.jdbc.UsingDriverManager <username> " +
                            "<password>");
            System.exit(1);
        }

        // Gets the username and password
        String username = args[0];
        String password = args[1];

        // Creates the datasource
        BasicDataSource dataSource = new BasicDataSource();

        // Configures the datasource
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // Creates connection with dataSource
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT first_name, last_name FROM actor " +
                             "WHERE last_name LIKE ?");
        ) {

            // Sets actor last name as parameter
            preparedStatement.setString(1, actorLastName);

            // Executes statement; displays results
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Displays list of all actors with given last name
                while (resultSet.next()) {

                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");

                    System.out.println(firstName + " " + lastName);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Gets first and last name inputs for next query
        System.out.println();
        System.out.print("Enter the first name of an actor whose movies you'd like to see displayed ➤ ");
        String actorFirstNameQ2 = scanner.nextLine();
        System.out.print("Now enter their last name ➤ ");
        String actorLastNameQ2 = scanner.nextLine();
        System.out.println();

        // Creates connection with dataSource
        try (Connection connection2 = dataSource.getConnection();
             PreparedStatement preparedStatement2 = connection2.prepareStatement(
                     "SELECT film.title " +
                             "FROM film " +
                             "INNER JOIN film_actor ON film.film_id = film_actor.film_id " +
                             "INNER JOIN actor ON actor.actor_id = film_actor.actor_id " +
                             "WHERE actor.first_name = ? AND actor.last_name = ?");
        ) {

            // Sets actor first and last name as parameters
            preparedStatement2.setString(1, actorFirstNameQ2);
            preparedStatement2.setString(2, actorLastNameQ2);

            // Executes statement; displays results
            try (ResultSet resultSet2 = preparedStatement2.executeQuery()) {

                // Displays list of all actors with given last name
                while (resultSet2.next()) {

                    // Prints movie title
                    System.out.println(resultSet2.getString("title"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}