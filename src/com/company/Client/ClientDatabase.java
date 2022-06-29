package com.company.Client;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


//! operatiile clasice CRUD
public class ClientDatabase {
    Connection connection;

    ClientFactory clientFactory = new ClientFactory();

    public ClientDatabase(Connection connection) {
        this.connection = connection;
    }

    public List<Client> read() throws SQLException {
        List<Client> clients = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Clients");
        while (result.next()) {
            Client current = clientFactory.createClient(result);
            clients.add(current);
        }
        statement.close();
        return clients;
    }

    public void update(Client newClient) throws SQLException {
        String query = "UPDATE Clients SET name = ?, CNP = ?, birthDate = ?, email = ?, phone = ?, street = ?, city = ?, county = ?, postalCode = ? WHERE clientId = ?";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, newClient.getName());
        preparedStmt.setString(2, newClient.getCNP());
        preparedStmt.setString(3, (new SimpleDateFormat("yyyy-MM-dd")).format(newClient.getBirthDate()));
        preparedStmt.setString(4, newClient.getEmail());
        preparedStmt.setString(5, newClient.getPhone());
        preparedStmt.setString(6, newClient.getAddress().getStreet());
        preparedStmt.setString(7, newClient.getAddress().getCity());
        preparedStmt.setString(8, newClient.getAddress().getCounty());
        preparedStmt.setInt(9, newClient.getAddress().getPostalCode());
        preparedStmt.setInt(10, newClient.getClientId());
        preparedStmt.executeUpdate();
        preparedStmt.close();
    }

    public void create(Client client) throws SQLException {
        String query = "INSERT INTO Clients (clientId, name, CNP, birthDate, email, phone, street, city, county, postalCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setInt(1, client.getClientId());
        preparedStmt.setString(2, client.getName());
        preparedStmt.setString(3, client.getCNP());
        preparedStmt.setString(4, (new SimpleDateFormat("yyyy-MM-dd")).format(client.getBirthDate()));
        preparedStmt.setString(5, client.getEmail());
        preparedStmt.setString(6, client.getPhone());
        preparedStmt.setString(7, client.getAddress().getStreet());
        preparedStmt.setString(8, client.getAddress().getCity());
        preparedStmt.setString(9, client.getAddress().getCounty());
        preparedStmt.setInt(10, client.getAddress().getPostalCode());
        preparedStmt.execute();
        preparedStmt.close();
    }

    public void delete(Client client) throws SQLException {
        String query = "DELETE FROM Clients WHERE clientId = ?";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setInt(1, client.getClientId());
        preparedStmt.execute();
        preparedStmt.close();
    }
}
