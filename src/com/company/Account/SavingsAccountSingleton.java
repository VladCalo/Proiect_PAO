package com.company.Account;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SavingsAccountSingleton {
    private static SavingsAccountSingleton instance = null;
    private List<SavingsAccount> savingsAccounts = new ArrayList<SavingsAccount>();

    public static SavingsAccountSingleton getInstance() {
        if (instance == null)
            instance = new SavingsAccountSingleton();
        return instance;
    }

    public void setSavingsAccounts(List<SavingsAccount> savingsAccounts) {
        this.savingsAccounts = savingsAccounts;
    }

    public List<SavingsAccount> getSavingsAccounts() {
        return savingsAccounts;
    }

    private static List<String[]> getColumns() throws IOException {

        List<String[]> columns = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader("/Users/vladcalomfirescu/Desktop/Vlad/FAC/an 2/sem 2/PAO/Proiect/src/com/company/data/savings_account.csv"));

        String line;

        while ((line = in.readLine()) != null) {
            String[] tokens = line.replaceAll(" ", "").split(",");
            columns.add(tokens);
        }

        return columns;
    }

    public void loadFromCSV() throws IOException, ParseException {
        List<String[]> columns = SavingsAccountSingleton.getColumns();
        for (String[] tokens : columns) {
            SavingsAccount newSavingsAccount = new SavingsAccount(
                    tokens[0],
                    tokens[1],
                    Double.parseDouble(tokens[2]),
                    tokens[3],
                    Integer.parseInt(tokens[4]),
                    new SimpleDateFormat("yyyy-MM-dd").parse(tokens[5]),
                    new SimpleDateFormat("yyyy-MM-dd").parse(tokens[6]),
                    Integer.parseInt(tokens[7])
            );
            savingsAccounts.add(newSavingsAccount);
        }
        AccountFactory.incrementUniqueId(columns.size());

    }

    public void dumpToCSV() throws IOException {

        var writer = new FileWriter("/Users/vladcalomfirescu/Desktop/Vlad/FAC/an 2/sem 2/PAO/Proiect/src/com/company/data/savings_account.csv");
        for (SavingsAccount account : this.savingsAccounts) {
            writer.write(account.toCSV());
            writer.write("\n");
        }
        writer.close();
    }
}
