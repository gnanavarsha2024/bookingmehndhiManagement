package com.mehandhi;

import java.sql.*;
import java.util.Scanner;

public class User {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookingmehndidb", "root", "vasan@1998");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Choose operation:");
                System.out.println("1. Create");
                System.out.println("2. Read");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Exit");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        createUser(con, scanner);
                        break;
                    case 2:
                        readUsers(con);
                        break;
                    case 3:
                        updateUser(con, scanner);
                        break;
                    case 4:
                        deleteUser(con, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    private static void createUser(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter user details:");
            System.out.println("UserID:");
            int userID = scanner.nextInt();
            System.out.println("Username:");
            String username = scanner.next();
            System.out.println("Password:");
            String password = scanner.next();
            System.out.println("Email:");
            String email = scanner.next();
            System.out.println("Phone:");
            String phone = scanner.next();

            String query = "INSERT INTO user (UserID, Username, Password, Email, Phone) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userID);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, phone);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User created successfully");
                } else {
                    System.out.println("User creation failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void readUsers(Connection connection) {
        try {
            String query = "SELECT * FROM user";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int userID = resultSet.getInt("UserID");
                    String username = resultSet.getString("Username");
                    String password = resultSet.getString("Password");
                    String email = resultSet.getString("Email");
                    String phone = resultSet.getString("Phone");
                    System.out.println("UserID: " + userID + ", Username: " + username + ", Password: " + password + ", Email: " + email + ", Phone: " + phone);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateUser(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter UserID of the user to update:");
            int userID = scanner.nextInt();
            System.out.println("Enter new username:");
            String newUsername = scanner.next();
            System.out.println("Enter new password:");
            String newPassword = scanner.next();
            System.out.println("Enter new email:");
            String newEmail = scanner.next();
            System.out.println("Enter new phone:");
            String newPhone = scanner.next();

            String query = "UPDATE user SET Username=?, Password=?, Email=?, Phone=? WHERE UserID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, newUsername);
                preparedStatement.setString(2, newPassword);
                preparedStatement.setString(3, newEmail);
                preparedStatement.setString(4, newPhone);
                preparedStatement.setInt(5, userID);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User updated successfully");
                } else {
                    System.out.println("User update failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteUser(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter UserID of the user to delete:");
            int userID = scanner.nextInt();

            String query = "DELETE FROM user WHERE UserID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userID);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User deleted successfully");
                } else {
                    System.out.println("User deletion failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
