package com.company.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TransactionSingleton {

    //! instanta este statica
    private static TransactionSingleton instance = null;

    private List<Transaction> transactions = new ArrayList<Transaction>();

    //crearea unei instante unice
    public static TransactionSingleton getInstance() {
        if (instance == null)
            instance = new TransactionSingleton();
        return instance;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    private static List<String[]> getColumns() throws IOException {
        List<String[]> columns = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader("/Users/vladcalomfirescu/Desktop/Vlad/FAC/an 2/sem 2/PAO/Proiect/src/com/company/data/transactions.csv"));

        String line;

        while ((line = in.readLine()) != null) {
            String[] tokens = line.replaceAll(" ", "").split(",");
            columns.add(tokens);
        }

        return columns;
    }

    public void loadFromCSV() throws Exception {

        List<String[]> columns = TransactionSingleton.getColumns();
        for (String[] tokens : columns) {
            Transaction newTransaction = new Transaction(
                    tokens[0],
                    tokens[1],
                    Double.parseDouble(tokens[2]),
                    new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss").parse(tokens[3])
            );
            transactions.add(newTransaction);
        }

    }

    public void dumpToCSV() throws IOException {

        FileWriter writer = new FileWriter("/Users/vladcalomfirescu/Desktop/Vlad/FAC/an 2/sem 2/PAO/Proiect/src/com/company/data/transactions.csv");
        for (Transaction transaction : this.transactions) {
            writer.write(transaction.toCSV());
            writer.write("\n");
        }
        writer.close();
    }
}
