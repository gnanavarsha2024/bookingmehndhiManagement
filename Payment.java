package com.mehandhi;

import java.sql.*;

import java.util.Scanner;

public class Payment {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookingmehndidb1", "root", "vasan@1998");

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
                        createPayment(con, scanner);
                        break;
                    case 2:
                        readPayments(con);
                        break;
                    case 3:
                        updatePayment(con, scanner);
                        break;
                    case 4:
                        deletePayment(con, scanner);
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

    private static void createPayment(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter payment details:");
            System.out.println("PaymentID:");
            int paymentID = scanner.nextInt();
            System.out.println("UserID:");
            int userID = scanner.nextInt();
            System.out.println("BookingID:");
            int bookingID = scanner.nextInt();
            System.out.println("Amount:");
            double amount = scanner.nextDouble();
            System.out.println("PaymentDate (YYYY-MM-DD):");
            String paymentDate = scanner.next();
            System.out.println("PaymentMethod:");
            String paymentMethod = scanner.next();
            System.out.println("Status:");
            String status = scanner.next();

            String query = "INSERT INTO payment (PaymentID, UserID, BookingID, Amount, PaymentDate, PaymentMethod, Status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, paymentID);
                preparedStatement.setInt(2, userID);
                preparedStatement.setInt(3, bookingID);
                preparedStatement.setDouble(4, amount);
                preparedStatement.setString(5, paymentDate);
                preparedStatement.setString(6, paymentMethod);
                preparedStatement.setString(7, status);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Payment created successfully");
                } else {
                    System.out.println("Payment creation failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void readPayments(Connection connection) {
        try {
            String query = "SELECT * FROM payment";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int paymentID = resultSet.getInt("PaymentID");
                    int userID = resultSet.getInt("UserID");
                    int bookingID = resultSet.getInt("BookingID");
                    double amount = resultSet.getDouble("Amount");
                    String paymentDate = resultSet.getString("PaymentDate");
                    String paymentMethod = resultSet.getString("PaymentMethod");
                    String status = resultSet.getString("Status");
                    System.out.println("PaymentID: " + paymentID + ", UserID: " + userID + ", BookingID: " + bookingID + ", Amount: " + amount + ", PaymentDate: " + paymentDate + ", PaymentMethod: " + paymentMethod + ", Status: " + status);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updatePayment(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter PaymentID of the payment to update:");
            int paymentID = scanner.nextInt();
            System.out.println("Enter new UserID:");
            int newUserID = scanner.nextInt();
            System.out.println("Enter new BookingID:");
            int newBookingID = scanner.nextInt();
            System.out.println("Enter new Amount:");
            double newAmount = scanner.nextDouble();
            System.out.println("Enter new PaymentDate (YYYY-MM-DD):");
            String newPaymentDate = scanner.next();
            System.out.println("Enter new PaymentMethod:");
            String newPaymentMethod = scanner.next();
            System.out.println("Enter new Status:");
            String newStatus = scanner.next();

            String query = "UPDATE payment SET UserID=?, BookingID=?, Amount=?, PaymentDate=?, PaymentMethod=?, Status=? WHERE PaymentID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, newUserID);
                preparedStatement.setInt(2, newBookingID);
                preparedStatement.setDouble(3, newAmount);
                preparedStatement.setString(4, newPaymentDate);
                preparedStatement.setString(5, newPaymentMethod);
                preparedStatement.setString(6, newStatus);
                preparedStatement.setInt(7, paymentID);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Payment updated successfully");
                } else {
                    System.out.println("Payment update failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deletePayment(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter PaymentID of the payment to delete:");
            int paymentID = scanner.nextInt();

            String query = "DELETE FROM payment WHERE PaymentID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, paymentID);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Payment deleted successfully");
                } else {
                    System.out.println("Payment deletion failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
