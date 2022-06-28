package com.company.Client;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ClientSingleton {
    private static ClientSingleton instance = null;
    private List<Client> clients = new ArrayList<Client>();

    public static ClientSingleton getInstance() {
        if (instance == null)
            instance = new ClientSingleton();
        return instance;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Client> getClients() {
        return clients;
    }

    private static List<String[]> getColumns() throws IOException {

        List<String[]> columns = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader("/Users/vladcalomfirescu/Desktop/Vlad/FAC/an 2/sem 2/PAO/Proiect/src/com/company/data/clients.csv"));

        String line;

        while ((line = in.readLine()) != null) {
            String[] tokens = line.replaceAll(" ", "").split(",");
            columns.add(tokens);
        }

        return columns;
    }

    public void loadFromCSV() throws IOException, ParseException {

        List<String[]> columns = ClientSingleton.getColumns();
        for (String[] tokens : columns) {
            Client newClient = new Client(
                    Integer.parseInt(tokens[0]),
                    tokens[1],
                    tokens[2],
                    new SimpleDateFormat("yyyy-MM-dd").parse(tokens[3]),
                    tokens[4],
                    tokens[5],
                    new Address(tokens[6], tokens[7], tokens[8], Integer.parseInt(tokens[9]))
            );
            clients.add(newClient);
        }
        ClientFactory.incrementUniqueId(columns.size());

    }

    public void dumpToCSV() throws IOException {
        FileWriter writer = new FileWriter("/Users/vladcalomfirescu/Desktop/Vlad/FAC/an 2/sem 2/PAO/Proiect/src/com/company/data/clients.csv");
        for (Client client : this.clients) {
            writer.write(client.toCSV());
            writer.write("\n");
        }
        writer.close();

    }
}
