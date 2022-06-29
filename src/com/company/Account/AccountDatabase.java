package com.company.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


//! CRUD
public class AccountDatabase {
    Connection connection;

    public AccountDatabase(Connection connection) {
        this.connection = connection;
    }

    //citim si returnam conturile folosinf "SELECT <<all>> FROM Accounts"
    public List<Account> read() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Accounts");
        while (result.next()) {
            Account current = new Account(result);
            accounts.add(current);
        }
        statement.close();
        return accounts;
    }

    //update la suma, nume si clientId dupa IBAN si SWIFT
    public void update(Account newAccount) throws SQLException {
        String query = "UPDATE Accounts SET amount = ?, name = ?, clientId = ? WHERE IBAN = ? AND swift = ?";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setDouble(1, newAccount.getAmount());
        preparedStmt.setString(2, newAccount.getName());
        preparedStmt.setInt(3, newAccount.getClientId());
        preparedStmt.setString(4, newAccount.getIBAN());
        preparedStmt.setString(5, newAccount.getSwift());
        preparedStmt.executeUpdate();
        preparedStmt.close();
    }

    // creaza conturile
    public void create(Account account) throws SQLException {
        String query = "INSERT INTO Accounts (IBAN, swift, amount, name, clientId) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, account.getIBAN());
        preparedStmt.setString(2, account.getSwift());
        preparedStmt.setDouble(3, account.getAmount());
        preparedStmt.setString(4, account.getName());
        preparedStmt.setInt(5, account.getClientId());
        preparedStmt.execute();
        preparedStmt.close();
    }

    //sterge conturile identificandu le dupa IBAN
    public void delete(Account account) throws SQLException {
        String query = "DELETE FROM Accounts WHERE IBAN = ?";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, account.getIBAN());
        preparedStmt.execute();
        preparedStmt.close();

    }
}

