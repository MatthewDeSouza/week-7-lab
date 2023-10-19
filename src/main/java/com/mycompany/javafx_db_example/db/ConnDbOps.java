package com.mycompany.javafx_db_example.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MoaathAlrajab
 */
public class ConnDbOps {

    private static final String MYSQL_SERVER_URL = "jdbc:mysql://localhost/";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "mysql";


    public boolean connectToDatabase() {
        boolean hasRegistredUsers = false;
        try {
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS project");
            statement.close();
            conn.close();

            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT( 10 ) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(200) NOT NULL,"
                    + "email VARCHAR(200) NOT NULL UNIQUE,"
                    + "phone VARCHAR(200),"
                    + "address VARCHAR(200),"
                    + "password VARCHAR(200) NOT NULL,"
                    + "salary DECIMAL(10, 2) NOT NULL"  // Added salary column
                    + ")";
            statement.executeUpdate(sql);

            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");

            if (resultSet.next()) {
                int numUsers = resultSet.getInt(1);
                if (numUsers > 0) {
                    hasRegistredUsers = true;
                }
            }

            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasRegistredUsers;
    }

    public Person queryUserByName(String name) {
        Person person = null;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM users WHERE name = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                double salary = resultSet.getDouble("salary");
                String password = resultSet.getString("password");
                person = new Person(id, name, email, phone, address, password, salary);
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return person;
    }

    public List<Person> listAllUsers() {
        List<Person> persons = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM users";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                double salary = resultSet.getDouble("salary");
                String password = resultSet.getString("password");
                persons.add(new Person(id, name, email, phone, address, password, salary));
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return persons;
    }

    public void insertUser(Person person) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO users (name, email, phone, address, password, salary) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getEmail());
            preparedStatement.setString(3, person.getPhone());
            preparedStatement.setString(4, person.getAddress());
            preparedStatement.setString(5, person.getPassword());
            preparedStatement.setDouble(6, person.getSalary());

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("A new user was inserted successfully.");
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(Person person) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE users SET name = ?, email = ?, phone = ?, address = ?, password = ?, salary = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getEmail());
            preparedStatement.setString(3, person.getPhone());
            preparedStatement.setString(4, person.getAddress());
            preparedStatement.setString(5, person.getPassword());
            preparedStatement.setDouble(6, person.getSalary());
            preparedStatement.setInt(7, person.getId());

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("User details were updated successfully.");
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("User was deleted successfully.");
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
