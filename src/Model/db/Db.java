package Model.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Db {
    private static final String URL = "jdbc:mysql://localhost:3306/RelaxGaming";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;

    static {
        try {
            try (Connection initConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=" + USER + "&password=" + PASSWORD);
                 Statement stmt = initConn.createStatement()) {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS RelaxGaming");
                System.out.println("Database RelaxGaming created or already exists.");
            } catch (SQLException e) {
                System.err.println("Failed to create database: " + e.getMessage());
            }
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection: " + e.getMessage());
        }
    }

    public static void run() throws SQLException {
        String accountsTable =
                "CREATE TABLE IF NOT EXISTS accounts (" +
                        "    account_number VARCHAR(50) PRIMARY KEY," +
                        "    account_balance DOUBLE NOT NULL DEFAULT 0" +
                        ");";
        String transactionTable =
                "CREATE TABLE IF NOT EXISTS transactions (" +
                        "    id INT AUTO_INCREMENT PRIMARY KEY," +
                        "    account_number VARCHAR(50) NOT NULL," +
                        "    description ENUM('win', 'lost', 'withdraw', 'deposit') NOT NULL," +
                        "    amount DOUBLE NOT NULL," +
                        "    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                        ");";
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(accountsTable);
            stmt.executeUpdate(transactionTable);
            System.out.println("Tables created or already exist.");
        } catch (SQLException e) {
            System.err.println("Error running query: " + e.getMessage());
        }
    }


    public static void createAccountIfNotExists(String accountNumber) throws SQLException {
        String sql = "INSERT IGNORE INTO accounts (account_number, account_balance) VALUES (?, 0)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ps.executeUpdate();
        }
    }

    public static List<DbRow> getTransactions(String accountNumber) throws Exception {
        String query = "SELECT * FROM transactions WHERE account_number = ?";
        List<DbRow> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(DbRow.fromResultSet(rs));
            }
        }
        return result;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Database connection is closed");
        }
    }

    public static void insertTransaction(String accountNumber, String type, float amount) throws SQLException {
        String sql = "INSERT INTO transactions (account_number, description, amount) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ps.setString(2, type);
            ps.setFloat(3, amount);
            ps.executeUpdate();
        }
    }

    public static float getAccountBalance(String accountNumber) throws SQLException {
        float balance = 0;
        String sql = "SELECT account_balance FROM accounts WHERE account_number=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                balance = rs.getFloat("account_balance");
            }
        }
        return balance;
    }

    public static void updateAccountBalance(String accountNumber, float balance) throws SQLException {
        String sql = "UPDATE accounts SET account_balance=? WHERE account_number=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setFloat(1, balance);
            ps.setString(2, accountNumber);
            ps.executeUpdate();
        }
    }
}
