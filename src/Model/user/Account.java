package Model.user;

import Model.db.Db;
import Model.db.DbRow;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account {
    private final String accountNumber;
    private float accountBalance;


    public Account(String accountNumber) throws SQLException {
        this.accountNumber = accountNumber;
        Db.createAccountIfNotExists(accountNumber);
        this.accountBalance = Db.getAccountBalance(accountNumber);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public float getAccountBalance() {
        return (float) (accountBalance * 1.10);
    }


    public void setAccountBalance(float balance) throws SQLException {
        this.accountBalance = balance;

        float amountInPounds = (float) (balance / 1.10);

        Db.updateAccountBalance(accountNumber, amountInPounds);
    }

    public List<Transaction> getTransactions() throws Exception {
        List<DbRow> dbTransactionList = Db.getTransactions(accountNumber.trim());
        ArrayList<Transaction> transactionList = new ArrayList<>();
        for (DbRow dbRow : dbTransactionList) {
            Transaction trans = makeTransactionFromDbRow(dbRow);
            String[] timestamps = createTimestampAndExpiryDate();
            trans.setTimestamp(timestamps[0]);
            trans.setExpiryDate(timestamps[1]);
            transactionList.add(trans);
        }

        return transactionList;

    }

    private Transaction makeTransactionFromDbRow(DbRow row) {
        double amountInPounds = Double.parseDouble(row.getValueForField("amount"));
        float amountInEuros = (float) (amountInPounds * 1.10);
        String description = row.getValueForField("description");
        return new Transaction(description, amountInEuros);
    }

    private String[] createTimestampAndExpiryDate() {
        String[] result = new String[2];
        LocalDateTime now = LocalDateTime.now();
        result[0] = now.toString();
        result[1] = now.plusDays(60).toString();
        return result;
    }

    public void applyTransaction(boolean win, float amount) throws Exception {
        float amountInPounds = (float) (amount / 1.10);

        if (win) {
            setAccountBalance(accountBalance + amountInPounds);
            Db.insertTransaction(accountNumber, "win", amountInPounds);
        } else {
            setAccountBalance(accountBalance - amountInPounds);
            Db.insertTransaction(accountNumber, "lost", -amountInPounds);
        }
    }

    private String fixDescription(String desc) {
        return "Transaction [" + desc + "]";
    }

    public boolean equals(Account o) {
        return Objects.equals(o.getAccountNumber(), getAccountNumber());
    }

}