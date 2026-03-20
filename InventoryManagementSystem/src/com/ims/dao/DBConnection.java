package com.ims.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // The one and only instance of this class
    private static DBConnection instance = null;

    // The actual database connection object
    private Connection connection;

    // Private constructor — nobody outside can call new DBConnection()
    private DBConnection() {
        try {
            String url      = "jdbc:mysql://localhost:3306/inventory_db";
            String user     = "root";
            String password = "#1Q2W3E4R5T#"; // PUT YOUR MYSQL PASSWORD HERE
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("Database connection FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // This is how every other class gets the connection
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection(); // only created once
        }
        return instance;
    }

    // Returns the Connection object to run SQL queries
    public Connection getConnection() {
        return connection;
    }
}