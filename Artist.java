package com.mehandhi;

import java.sql.*;
import java.util.Scanner;

public class Artist {
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
                        createArtist(con, scanner);
                        break;
                    case 2:
                        readArtists(con);
                        break;
                    case 3:
                        updateArtist(con, scanner);
                        break;
                    case 4:
                        deleteArtist(con, scanner);
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

    private static void createArtist(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter artist details:");
            System.out.println("ArtistID:");
            int artistID = scanner.nextInt();
            System.out.println("Name:");
            String name = scanner.next();
            System.out.println("Specialty:");
            String specialty = scanner.next();
            System.out.println("Phone:");
            String phone = scanner.next();
            System.out.println("Email:");
            String email = scanner.next();

            String query = "INSERT INTO artists (ArtistID, Name, Specialty, Phone, Email) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, artistID);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, specialty);
                preparedStatement.setString(4, phone);
                preparedStatement.setString(5, email);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Artist created successfully");
                } else {
                    System.out.println("Artist creation failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void readArtists(Connection connection) {
        try {
            String query = "SELECT * FROM artist";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int artistID = resultSet.getInt("ArtistID");
                    String name = resultSet.getString("Name");
                    String specialty = resultSet.getString("Specialty");
                    String phone = resultSet.getString("Phone");
                    String email = resultSet.getString("Email");
                    System.out.println("ArtistID: " + artistID + ", Name: " + name + ", Specialty: " + specialty + ", Phone: " + phone + ", Email: " + email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateArtist(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter ArtistID of the artist to update:");
            int artistID = scanner.nextInt();
            System.out.println("Enter new name:");
            String newName = scanner.next();
            System.out.println("Enter new specialty:");
            String newSpecialty = scanner.next();
            System.out.println("Enter new phone:");
            String newPhone = scanner.next();
            System.out.println("Enter new email:");
            String newEmail = scanner.next();

            String query = "UPDATE artists SET Name=?, Specialty=?, Phone=?, Email=? WHERE ArtistID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, newName);
                preparedStatement.setString(2, newSpecialty);
                preparedStatement.setString(3, newPhone);
                preparedStatement.setString(4, newEmail);
                preparedStatement.setInt(5, artistID);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Artist updated successfully");
                } else {
                    System.out.println("Artist update failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteArtist(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter ArtistID of the artist to delete:");
            int artistID = scanner.nextInt();

            String query = "DELETE FROM artists WHERE ArtistID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, artistID);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Artist deleted successfully");
                } else {
                    System.out.println("Artist deletion failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
