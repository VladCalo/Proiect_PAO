package com.company.Account;

public class AccountFactory {
    private static int id = 0;

    public static void incrementUniqueId(int i) {
        AccountFactory.id += i;
    }

    public Account createAccount(String name, int clientId) {
        return new Account(name, clientId, id++);
    }

    public SavingsAccount createSavingsAccount(String name, int clientId) {
        return new SavingsAccount(name, clientId, id++);
    }
}
