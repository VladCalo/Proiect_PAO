package com.company.Account;

import com.company.Card.Card;
import com.company.Card.CardFactory;
import com.company.Transaction.CompTransaction;
import com.company.Transaction.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account {
    protected String IBAN;
    protected String swift;
    protected double amount;
    protected String name;
    protected int clientId;

    protected List<Card> cards = new ArrayList<>();

    private final CardFactory cardFactory = new CardFactory();

    public Account(String IBAN, String swift, double amount, String name, int clientId) {
        this.IBAN = IBAN;
        this.swift = swift;
        this.amount = amount;
        this.name = name;
        this.clientId = clientId;

    }

    public Account(String name, int clientId, int id) {
        this.IBAN = this.generateIBAN(id);
        this.swift = this.generateSwift();
        this.amount = 0;
        this.name = name;
        this.clientId = clientId;
    }

    public Account(ResultSet in) throws SQLException {
        this.IBAN = in.getString("IBAN");
        this.swift = in.getString("swift");
        this.amount = in.getDouble("amount");
        this.name = in.getString("name");
        this.clientId = in.getInt("clientId");
    }

    public void addCard(String name) {
        Card newCard = cardFactory.addCard(this.IBAN, name);
        cards.add(newCard);
    }

    private String generateIBAN(int id) {
        return "RO06BRDB" + id;
    }

    private String generateSwift() {
        return "SWIFTBRDB";
    }

    public String toCSV() {
        return IBAN +
                "," + swift +
                "," + amount +
                "," + name +
                "," + clientId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "IBAN='" + IBAN + '\'' +
                ", swift='" + swift + '\'' +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                ", clientId=" + clientId +
                ", cards=" + cards +
                ", cardFactory=" + cardFactory +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.amount, amount) == 0 && clientId == account.clientId && IBAN.equals(account.IBAN) && swift.equals(account.swift) && name.equals(account.name) && cards.equals(account.cards) && cardFactory.equals(account.cardFactory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IBAN, swift, amount, name, clientId, cards, cardFactory);
    }

    public String getIBAN() {
        return IBAN;
    }

    public String getSwift() {
        return swift;
    }

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public int getClientId() {
        return clientId;
    }

    public List<Card> getCards() {
        return cards;
    }

    public CardFactory getCardFactory() {
        return cardFactory;
    }

    public List<Transaction> filterTransactions(List<Transaction> allTransactions) {
        List<Transaction> transactions = new ArrayList<>();
        for (var transaction : allTransactions)
            if (transaction.getFromIBAN().equals(this.IBAN))
                transactions.add(transaction);
        transactions.sort(new CompTransaction());
        return transactions;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
