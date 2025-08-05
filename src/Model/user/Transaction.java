package Model.user;

public class Transaction {
    private String description;
    private float amountInEuros;
    private String timestamp;
    private String expiryDate;

    public Transaction(String description, float amountInEuros) {
        this.description = description;
        this.amountInEuros = amountInEuros;
    }
    public Transaction(String description, float amountInEuros, String timestamp, String expiryDate) {
        this.description = description;
        this.amountInEuros = amountInEuros;
        this.timestamp = timestamp;
        this.expiryDate = expiryDate;
    }

    public String getDescription() {
        return description;
    }

    public float getAmountInEuros() {
        return amountInEuros;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
