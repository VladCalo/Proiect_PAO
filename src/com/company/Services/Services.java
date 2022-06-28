package com.company.Services;

import com.company.Account.*;
import com.company.Client.Client;
import com.company.Client.ClientDatabase;
import com.company.Client.ClientFactory;
import com.company.Transaction.Transaction;
import com.company.Transaction.TransactionDatabase;

import java.text.ParseException;
import java.util.*;

public class Services {
    private List<Client> clients = new ArrayList<>();
    private List<Account> accounts = new ArrayList<>();
    private List<SavingsAccount> savingsAccounts = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();

    private final Map<String, Account> accountsMap = new HashMap<>();
    private final ClientFactory clientFactory = new ClientFactory();
    private final AccountFactory accountFactory = new AccountFactory();

    public List<Client> getClients() {
        return clients;
    }
    public List<Account> getAccounts() {
        return accounts;
    }
    public List<SavingsAccount> getSavingsAccounts() {
        return savingsAccounts;
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setClients(List<Client> clients){
        this.clients = clients;
    }
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    public void setSavingsAccounts(List<SavingsAccount> savingsAccounts) {
        this.savingsAccounts = savingsAccounts;
    }
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    private ClientDatabase clientDatabase = null;
    private TransactionDatabase transactionDatabase = null;
    private AccountDatabase accountDatabase = null;
    private SavingsAccountDatabase savingsAccountDatabase = null;

    public Services(ClientDatabase clientDatabase, TransactionDatabase transactionDatabase, AccountDatabase accountDatabase, SavingsAccountDatabase savingsAccountDatabase) {
        this.clientDatabase = clientDatabase;
        this.transactionDatabase = transactionDatabase;
        this.accountDatabase = accountDatabase;
        this.savingsAccountDatabase = savingsAccountDatabase;

        this.clients = clientDatabase.read();
        this.transactions = transactionDatabase.read();
        this.accounts = accountDatabase.read();
        this.savingsAccounts = savingsAccountDatabase.read();

        this.linkAccounts();
    }


    public Services(){ }

    private Client getClientFromInput(Scanner in) throws Exception{
        if(this.clients.size()==0)
            throw new Exception("No clients added!");
        if(this.clients.size()==1)
            return clients.get(0);
        System.out.println("Client id [0-"+(this.clients.size()-1)+"]: ");
        int clientId = Integer.parseInt(in.nextLine());
        return clients.get(clientId);
    }

    public void linkAccounts(){
        for(Account account: this.accounts)
            this.accountsMap.put(account.getIBAN(), account);
    }

    public void createClient(Scanner in) throws ParseException {
        Client newClient = clientFactory.createClient(in);
        this.clients.add(newClient);
        Account newAccount = accountFactory.createAccount(newClient.getName() , newClient.getClientId());
        this.accounts.add(newAccount);
        System.out.println("Client created");
    }

    public void getClient(Scanner in) throws Exception {
        Client client = this.getClientFromInput(in);
        System.out.println(client.toString());
    }

    private Account getAccountFromInput(Scanner in, Client client) throws Exception {
        List<Account> clientsAccounts = client.filterAccounts(this.accounts);
        System.out.println("Client accounts: " + clientsAccounts);
        System.out.println("Choose IBAN: ");
        String IBAN = in.nextLine();
        if(!this.accountsMap.containsKey(IBAN))
            throw new Exception("Invalid IBAN number!");
        Account account = accountsMap.get(IBAN);;
        if(account.getClientId() != client.getClientId())
            throw new Exception("The given IBAN number is not associated with the selected client");
        return account;
    }

    public void getClientAmount(Scanner in) throws Exception {
        Client client = this.getClientFromInput(in);
        List<Account> clientAccounts = client.filterAccounts(this.accounts);
        double totalAmount = 0;
        for(Account account: clientAccounts)
            totalAmount += account.getAmount();
        System.out.println(client.getName()  + " has a total amount of: " + totalAmount + " lei in his accounts.");
    }

    public void getClientAccounts(Scanner in) throws Exception {
        Client client = this.getClientFromInput(in);
        List<Account> clientsAccounts = client.filterAccounts(this.accounts);
        System.out.println(clientsAccounts.toString());
    }

    public void createClientAccount(Scanner in) throws Exception {
        Client client = this.getClientFromInput(in);
        System.out.println("Account name: ");
        String name = in.nextLine();
        Account newAccount = this.accountFactory.createAccount(name, client.getClientId());
        accounts.add(newAccount);
        accountsMap.put(newAccount.getIBAN(), newAccount);
        System.out.println("Account created");
    }

    public void createClientSavingsAccount(Scanner in) throws Exception {
        Client client = this.getClientFromInput(in);
        System.out.println("Savings Account name: ");
        String name = in.nextLine();
        SavingsAccount newSavingsAccount = this.accountFactory.createSavingsAccount(name, client.getClientId());
        this.savingsAccounts.add(newSavingsAccount);
        System.out.println("Savings Account created");
    }

    public void createClientCard(Scanner in) throws Exception {
        Client client = this.getClientFromInput(in);
        Account account = this.getAccountFromInput(in, client);
        System.out.println("Card Holder name: ");
        String name = in.nextLine();
        account.addCard(name);
    }

    public void loadClientAccount(Scanner in) throws Exception {
        Client client = this.getClientFromInput(in);
        System.out.println("How much do you want to load into your account?: ");
        int amount = Integer.parseInt(in.nextLine());
        List<Account> clientAccounts = client.filterAccounts(this.accounts);
        clientAccounts.get(0).setAmount(amount);
        System.out.println("The account has been loaded!");
    }

    public void createTransaction(Scanner in) throws Exception {
        System.out.println("From account (IBAN): ");
        String IBAN1 = in.nextLine();
        System.out.println("To account (IBAN): ");
        String IBAN2 = in.nextLine();
        System.out.println("Amount: ");
        int amount = in.nextInt();
        Account account1 = null, account2 = null;

        if(accountsMap.containsKey(IBAN1))
            account1 = accountsMap.get(IBAN1);
        if(accountsMap.containsKey(IBAN2))
            account2 = accountsMap.get(IBAN2);

        if(IBAN1.equals(IBAN2))
            throw new Exception("Cannot send transaction to same account");
        if(account1==null || account2==null)
            throw new Exception("Cannot find IBAN numbers!");
        if(account1.getAmount() < amount)
            throw new Exception("Insufficient founds!");

        account1.setAmount(account1.getAmount() - amount);
        account2.setAmount(account2.getAmount() + amount);

        var newTransaction = new Transaction(IBAN1, IBAN2, amount);
        this.transactions.add(newTransaction);
        System.out.println("Transaction finished");
    }

    public void closeAccount(Scanner in) throws Exception {
        Client client = this.getClientFromInput(in);
        Account account = this.getAccountFromInput(in, client);

        if(client.filterAccounts(this.accounts).size()<=1)
            throw new Exception("There has to be at least one bank account associated with the user!");
        if(account.getAmount()!=0)
            throw new Exception("The account savings are not empty!");
        this.accountsMap.remove(account.getIBAN());
        this.accounts.remove(account);
        System.out.println("Account closed!");
    }

    public void getClientTransactions(Scanner in) throws Exception{
        Client client = this.getClientFromInput(in);
        System.out.println(client.filterTransactions(accounts, transactions));
        System.out.println();
    }
}
