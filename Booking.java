package com.mehandhi;

import java.sql.*;
import java.util.Scanner;

public class Booking {
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
                        createBooking(con, scanner);
                        break;
                    case 2:
                        readBookings(con);
                        break;
                    case 3:
                        updateBooking(con, scanner);
                        break;
                    case 4:
                        deleteBooking(con, scanner);
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

    private static void createBooking(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter booking details:");
            System.out.println("BookingID:");
            int bookingID = scanner.nextInt();
            System.out.println("UserID:");
            int userID = scanner.nextInt();
            System.out.println("ArtistID:");
            int artistID = scanner.nextInt();
            System.out.println("BookingDate (YYYY-MM-DD):");
            String bookingDate = scanner.next();
            System.out.println("BookingTime (HH:MM:SS):");
            String bookingTime = scanner.next();
            System.out.println("Status:");
            String status = scanner.next();
            System.out.println("PaymentStatus:");
            String paymentStatus = scanner.next();

            String query = "INSERT INTO booking (BookingID, UserID, ArtistID, BookingDate, BookingTime, Status, PaymentStatus) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, bookingID);
                preparedStatement.setInt(2, userID);
                preparedStatement.setInt(3, artistID);
                preparedStatement.setString(4, bookingDate);
                preparedStatement.setString(5, bookingTime);
                preparedStatement.setString(6, status);
                preparedStatement.setString(7, paymentStatus);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Booking created successfully");
                } else {
                    System.out.println("Booking creation failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void readBookings(Connection connection) {
        try {
            String query = "SELECT * FROM booking";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int bookingID = resultSet.getInt("BookingID");
                    int userID = resultSet.getInt("UserID");
                    int artistID = resultSet.getInt("ArtistID");
                    String bookingDate = resultSet.getString("BookingDate");
                    String bookingTime = resultSet.getString("BookingTime");
                    String status = resultSet.getString("Status");
                    String paymentStatus = resultSet.getString("PaymentStatus");
                    System.out.println("BookingID: " + bookingID + ", UserID: " + userID + ", ArtistID: " + artistID + ", BookingDate: " + bookingDate + ", BookingTime: " + bookingTime + ", Status: " + status + ", PaymentStatus: " + paymentStatus);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateBooking(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter BookingID of the booking to update:");
            int bookingID = scanner.nextInt();
            System.out.println("Enter new UserID:");
            int newUserID = scanner.nextInt();
            System.out.println("Enter new ArtistID:");
            int newArtistID = scanner.nextInt();
            System.out.println("Enter new BookingDate (YYYY-MM-DD):");
            String newBookingDate = scanner.next();
            System.out.println("Enter new BookingTime (HH:MM:SS):");
            String newBookingTime = scanner.next();
            System.out.println("Enter new Status:");
            String newStatus = scanner.next();
            System.out.println("Enter new PaymentStatus:");
            String newPaymentStatus = scanner.next();

            String query = "UPDATE booking SET UserID=?, ArtistID=?, BookingDate=?, BookingTime=?, Status=?, PaymentStatus=? WHERE BookingID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, newUserID);
                preparedStatement.setInt(2, newArtistID);
                preparedStatement.setString(3, newBookingDate);
                preparedStatement.setString(4, newBookingTime);
                preparedStatement.setString(5, newStatus);
                preparedStatement.setString(6, newPaymentStatus);
                preparedStatement.setInt(7, bookingID);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Booking updated successfully");
                } else {
                    System.out.println("Booking update failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteBooking(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter BookingID of the booking to delete:");
            int bookingID = scanner.nextInt();

            String query = "DELETE FROM booking WHERE BookingID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, bookingID);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Booking deleted successfully");
                } else {
                    System.out.println("Booking deletion failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
