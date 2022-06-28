package com.company.Account;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountSingleton {
    private static AccountSingleton instance = null;

    private List<Account> accounts = new ArrayList<>();

    public static AccountSingleton getInstance() {
        if (instance == null)
            instance = new AccountSingleton();
        return instance;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    private static List<String[]> getColumns() throws IOException {

        List<String[]> columns = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader("/Users/vladcalomfirescu/Desktop/Vlad/FAC/an 2/sem 2/PAO/Proiect/src/com/company/data/accounts.csv"));

        String line;

        while ((line = in.readLine()) != null) {
            String[] tokens = line.replaceAll(" ", "").split(",");
            columns.add(tokens);
        }
        return columns;
    }

    public void loadFromCSV() throws IOException {
        List<String[]> columns = AccountSingleton.getColumns();
        for (String[] tokens : columns) {
            Account newAccount = new Account(
                    tokens[0],
                    tokens[1],
                    Double.parseDouble(tokens[2]),
                    tokens[3],
                    Integer.parseInt(tokens[4])
            );
            accounts.add(newAccount);
        }
        AccountFactory.incrementUniqueId(columns.size());
    }

    public void dumpToCSV() throws IOException {
        FileWriter writer = new FileWriter("/Users/vladcalomfirescu/Desktop/Vlad/FAC/an 2/sem 2/PAO/Proiect/src/com/company/data/accounts.csv");
        for (Account account : this.accounts) {
            writer.write(account.toCSV());
            writer.write("\n");
        }
        writer.close();
    }
}
