package com.company.Transaction;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


//! CRUD
public class TransactionDatabase {
    Connection connection;

    public TransactionDatabase(Connection connection) {
        this.connection = connection;
    }

    public List<Transaction> read() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Transactions");
        while (result.next()) {
            Transaction current = new Transaction(result);
            transactions.add(current);
        }
        statement.close();
        return transactions;
    }

    public void create(Transaction transaction) throws SQLException {

        String query = "INSERT INTO Transactions (sourceIBAN, destIBAN, amount, description, creationDate) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, transaction.getFromIBAN());
        preparedStmt.setString(2, transaction.getToIBAN());
        preparedStmt.setDouble(3, transaction.getAmount());
        preparedStmt.setString(4, (new SimpleDateFormat("yyyy-MM-dd h:m:s")).format(transaction.getCreationDate()));
        preparedStmt.execute();
        preparedStmt.close();
    }

    public void delete(Transaction transaction) throws SQLException {
        String query = "DELETE FROM Transactions WHERE sourceIBAN = ?, destIBAN = ?, creationDate = ?";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, transaction.getFromIBAN());
        preparedStmt.setString(2, transaction.getToIBAN());
        preparedStmt.setString(3, (new SimpleDateFormat("yyyy-MM-dd h:m:s")).format(transaction.getCreationDate()));
        preparedStmt.execute();
        preparedStmt.close();
    }
}

