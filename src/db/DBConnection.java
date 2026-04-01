package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/smart_parking_db";
    private String username = "root";
    private String password = "sparkyop";

    private DBConnection() {
        // Load MySQL JDBC Driver - try with fallback
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver (com.mysql.cj) loaded successfully");
        } catch (ClassNotFoundException e1) {
            System.out.println("com.mysql.cj.jdbc.Driver not found, trying alternative driver...");
            try {
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("MySQL JDBC Driver (com.mysql) loaded successfully");
            } catch (ClassNotFoundException e2) {
                System.out.println("Both MySQL drivers not found - attempting to load via DriverManager");
            }
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Properties props = new Properties();
                props.setProperty("user", username);
                props.setProperty("password", password);
                props.setProperty("serverTimezone", "UTC");
                props.setProperty("useSSL", "false");
                props.setProperty("allowPublicKeyRetrieval", "true");
                
                connection = DriverManager.getConnection(url, props);
                System.out.println("Successfully connected to smart_parking_db");
            } catch (SQLException e) {
                System.err.println("Database Connection Error: " + e.getMessage());
                System.err.println("URL: " + url);
                System.err.println("Username: " + username);
                JOptionPane.showMessageDialog(null, "Failed to connect to database:\n" + e.getMessage() + "\n\nEnsure:\n1. MySQL is running\n2. Database 'smart_parking_db' exists\n3. Run SampleData.sql first");
                throw e;
            }
        }
        return connection;
    }
}
