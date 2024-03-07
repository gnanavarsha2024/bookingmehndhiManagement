package com.mehandhi;

import java.sql.*;
import java.util.Scanner;

public class Portfolio {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookingmehndidb", "root", "vasan@1998");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println(" Choose operation:");
                System.out.println("1. Create");
                System.out.println("2. Read");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Exit");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        createPortfolio(con, scanner);
                        break;
                    case 2:
                        readPortfolios(con);
                        break;
                    case 3:
                        updatePortfolio(con, scanner);
                        break;
                    case 4:
                        deletePortfolio(con, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        System.out.print(" Choose operation: "); // Print the cursor on the same line
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
                
                if (choice == 5) {
                    break; // Exit the loop after displaying "Exiting..."
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    private static void createPortfolio(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter portfolio details:");
            System.out.println("PortfolioID:");
            int portfolioID = scanner.nextInt();
            System.out.println("ArtistID:");
            int artistID = scanner.nextInt();
            System.out.println("Description:");
            String description = scanner.next();
            System.out.println("ImageURL:");
            String imageURL = scanner.next();

            String query = "INSERT INTO portfolio (PortfolioID, ArtistID, Description, ImageURL) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, portfolioID);
                preparedStatement.setInt(2, artistID);
                preparedStatement.setString(3, description);
                preparedStatement.setString(4, imageURL);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Portfolio created successfully");
                } else {
                    System.out.println("Portfolio creation failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void readPortfolios(Connection connection) {
        try {
            String query = "SELECT * FROM portfolio";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int portfolioID = resultSet.getInt("PortfolioID");
                    int artistID = resultSet.getInt("ArtistID");
                    String description = resultSet.getString("Description");
                    String imageURL = resultSet.getString("ImageURL");
                    System.out.println("PortfolioID: " + portfolioID + ", ArtistID: " + artistID + ", Description: " + description + ", ImageURL: " + imageURL);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updatePortfolio(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter PortfolioID of the portfolio to update:");
            int portfolioID = scanner.nextInt();
            System.out.println("Enter new ArtistID:");
            int newArtistID = scanner.nextInt();
            System.out.println("Enter new Description:");
            String newDescription = scanner.next();
            System.out.println("Enter new ImageURL:");
            String newImageURL = scanner.next();

            String query = "UPDATE portfolio SET ArtistID=?, Description=?, ImageURL=? WHERE PortfolioID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, newArtistID);
                preparedStatement.setString(2, newDescription);
                preparedStatement.setString(3, newImageURL);
                preparedStatement.setInt(4, portfolioID);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Portfolio updated successfully");
                } else {
                    System.out.println("Portfolio update failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deletePortfolio(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter PortfolioID of the portfolio to delete:");
            int portfolioID = scanner.nextInt();

            String query = "DELETE FROM portfolio WHERE PortfolioID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, portfolioID);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Portfolio deleted successfully");
                } else {
                    System.out.println("Portfolio deletion failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
