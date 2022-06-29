//
//  Main.java
//  Proiect
//
//  Created by Vlad Calomfirescu on 27.03.2022.
//

package com.company;

import com.company.Account.AccountDatabase;
import com.company.Account.SavingsAccountDatabase;
import com.company.Client.ClientDatabase;
import com.company.Services.Audit;
import com.company.Services.Services;
import com.company.Transaction.TransactionDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    //comenzile din meniu
    static List<Integer> commands = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    //comenzile folosite la audit
    static List<String> availableCommands = Arrays.asList("1. create_client", "2. create_client_card", "3. get_client", "4. get_client_amount", "5. get_client_accounts", "6. load_client_account", "7. create_transaction", "8. create_client_account", "9. create_client_savings_account", "10. close_client_account", "11. get_client_transactions", "12. end");


    //conetctare la BD
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/PAO";
        String user = "root";
        String password = "";

        return DriverManager.getConnection(url, user, password);
    }


    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        boolean end = false;
        Connection connection = Main.getConnection();

        ClientDatabase clientDatabase = new ClientDatabase(connection);
        TransactionDatabase transactionDatabase = new TransactionDatabase(connection);
        AccountDatabase accountDatabase = new AccountDatabase(connection);
        SavingsAccountDatabase savingsAccountDatabase = new SavingsAccountDatabase(connection);

        Services services = new Services(clientDatabase, transactionDatabase, accountDatabase, savingsAccountDatabase);
        Audit audit = new Audit();


//        Services services = new Services();
//        Audit audit = new Audit();

//        ClientSingleton.getInstance().loadFromCSV();
//        AccountSingleton.getInstance().loadFromCSV();
//        SavingsAccountSingleton.getInstance().loadFromCSV();
//        TransactionSingleton.getInstance().loadFromCSV();

//        services.setClients(ClientSingleton.getInstance().getClients());
//        services.setAccounts(AccountSingleton.getInstance().getAccounts());
//        services.setSavingsAccounts(SavingsAccountSingleton.getInstance().getSavingsAccounts());
//        services.setTransactions(TransactionSingleton.getInstance().getTransactions());
//
//        services.linkAccounts();

        while (!end) {
            System.out.println(
                    """
                            \n
                            Options:
                            1. Create client
                            2. Create client card
                            3. Get client
                            4. Get client amount
                            5. Get client accounts
                            6. Load client account
                            7. Create transaction
                            8. Create client account
                            9. Create client savings account
                            10. Close client account
                            11. Get client transactions
                            12. End

                            """
            );
            int command = in.nextInt();
            switch (command) {
                case 1 -> services.createClient(in);
                case 2 -> services.createClientCard(in);
                case 3 -> services.getClient(in);
                case 4 -> services.getClientAmount(in);
                case 5 -> services.getClientAccounts(in);
                case 6 -> services.loadClientAccount(in);
                case 7 -> services.createTransaction(in);
                case 8 -> services.createClientAccount(in);
                case 9 -> services.createClientSavingsAccount(in);
                case 10 -> services.closeAccount(in);
                case 11 -> services.getClientTransactions(in);
                case 12 -> end = true;
            }
            //salvare in audit
            if (commands.contains(command))
                audit.logAction(availableCommands.get(commands.indexOf(command)));
        }

        try {
            assert connection != null;
            connection.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

//        ClientSingleton.getInstance().setClients(services.getClients());
//        AccountSingleton.getInstance().setAccounts(services.getAccounts());
//        SavingsAccountSingleton.getInstance().setSavingsAccounts(services.getSavingsAccounts());
//        TransactionSingleton.getInstance().setTransactions(services.getTransactions());
//
//        ClientSingleton.getInstance().dumpToCSV();
//        AccountSingleton.getInstance().dumpToCSV();
//        SavingsAccountSingleton.getInstance().dumpToCSV();
//        TransactionSingleton.getInstance().dumpToCSV();


//        Scanner in = new Scanner(System.in);
//        boolean end = false;
//        Services services = new Services();
//        Audit audit = new Audit();
//
//        ClientSingleton.getInstance().loadFromCSV();
//        AccountSingleton.getInstance().loadFromCSV();
//        SavingsAccountSingleton.getInstance().loadFromCSV();
//        TransactionSingleton.getInstance().loadFromCSV();
//
//        services.setClients(ClientSingleton.getInstance().getClients());
//        services.setAccounts(AccountSingleton.getInstance().getAccounts());
//        services.setSavingsAccounts(SavingsAccountSingleton.getInstance().getSavingsAccounts());
//        services.setTransactions(TransactionSingleton.getInstance().getTransactions());
//
//        services.linkAccounts();
//
//        while (!end) {
//            System.out.println(
//                    """
//                            \n
//                            Options:
//                            1. Create client
//                            2. Create client card
//                            3. Get client
//                            4. Get client amount
//                            5. Get client accounts
//                            6. Load client account
//                            7. Create transaction
//                            8. Create client account
//                            9. Create client savings account
//                            10. Close client account
//                            11. Get client transactions
//                            12. End
//
//                            """
//            );
//            int command = in.nextInt();
//            switch (command) {
//                case 1 -> services.createClient(in);
//                case 2 -> services.createClientCard(in);
//                case 3 -> services.getClient(in);
//                case 4 -> services.getClientAmount(in);
//                case 5 -> services.getClientAccounts(in);
//                case 6 -> services.loadClientAccount(in);
//                case 7 -> services.createTransaction(in);
//                case 8 -> services.createClientAccount(in);
//                case 9 -> services.createClientSavingsAccount(in);
//                case 10 -> services.closeAccount(in);
//                case 11 -> services.getClientTransactions(in);
//                case 12 -> end = true;
//            }
//            if (commands.contains(command))
//                audit.logAction(availableCommands.get(commands.indexOf(command)));
//        }
//
//        ClientSingleton.getInstance().setClients(services.getClients());
//        AccountSingleton.getInstance().setAccounts(services.getAccounts());
//        SavingsAccountSingleton.getInstance().setSavingsAccounts(services.getSavingsAccounts());
//        TransactionSingleton.getInstance().setTransactions(services.getTransactions());
//
//        ClientSingleton.getInstance().dumpToCSV();
//        AccountSingleton.getInstance().dumpToCSV();
//        SavingsAccountSingleton.getInstance().dumpToCSV();
//        TransactionSingleton.getInstance().dumpToCSV();

    }
}
