package com.company.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Transaction {
    final private String sourceIBAN;
    private final String destIBAN;
    final private double amount;
    final private Date creationDate;

    public Transaction(String sourceIBAN, String destIBAN, double amount) throws Exception {
        //verificam amount deoarece nu este permisa o tranzactie cu suma <=0
        if (amount <= 0)
            throw new Exception("The given amount is too low!");
        this.sourceIBAN = sourceIBAN;
        this.destIBAN = destIBAN;
        this.amount = amount;
        this.creationDate = new Date();
    }

    public Transaction(String sourceIBAN, String destIBAN, double amount, Date creationDate) throws Exception {
        this.sourceIBAN = sourceIBAN;
        this.destIBAN = destIBAN;
        this.amount = amount;
        this.creationDate = creationDate;
    }

    //etapa 3
    public Transaction(ResultSet in) throws SQLException {
        this.sourceIBAN = in.getString("sourceIBAN");
        this.destIBAN = in.getString("destIBAN");
        this.amount = in.getDouble("amount");
        this.creationDate = in.getDate("creationDate");
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sourceIBAN='" + sourceIBAN + '\'' +
                ", destIBAN='" + destIBAN + '\'' +
                ", amount=" + amount +
                ", creationDate=" + creationDate +
                '}';
    }

    public String toCSV() {
        return sourceIBAN +
                "," + destIBAN +
                "," + amount +
                "," + (new SimpleDateFormat("yyyy-MM-dd h:m:s")).format(creationDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 && sourceIBAN.equals(that.sourceIBAN) && destIBAN.equals(that.destIBAN) && creationDate.equals(that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceIBAN, destIBAN, amount, creationDate);
    }

    public String getFromIBAN() {
        return sourceIBAN;
    }

    public String getToIBAN() {
        return destIBAN;
    }

    public double getAmount() {
        return amount;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
