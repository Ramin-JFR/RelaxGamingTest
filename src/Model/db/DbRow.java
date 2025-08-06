package Model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DbRow {
    private int id;
    private String accountNumber;
    private String description;
    private double amount;
    private Timestamp transactionDate;

    public static DbRow fromResultSet(ResultSet rs) throws SQLException {
        DbRow row = new DbRow();
        row.id = rs.getInt("id");
        row.accountNumber = rs.getString("account_number");
        row.description = rs.getString("description");
        row.amount = rs.getDouble("amount");
        row.transactionDate = rs.getTimestamp("transaction_date");
        return row;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public String getValueForField(String field) {
        switch (field) {
            case "id":
                return String.valueOf(id);
            case "account_number":
                return accountNumber;
            case "description":
                return description;
            case "amount":
                return String.valueOf(amount);
            case "transaction_date":
                return transactionDate != null ? transactionDate.toString() : null;
            default:
                throw new IllegalArgumentException("Unknown field: " + field);
        }
    }

}
