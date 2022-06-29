package com.company.Client;

import com.company.Account.Account;
import com.company.Transaction.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Client {
    private final int clientId;
    private String name;
    private String CNP;
    private Date birthDate;
    private String email;
    private String phone;
    private Address address;

    //constructor etapa 1
    public Client(int clientId, String name, String CNP, Date birthDate, String email, String phone, Address address) {
        this.clientId = clientId;
        this.name = name;
        this.CNP = CNP;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    //constructor cu citire de la tastatura
    public Client(int clientId, Scanner in) throws ParseException {
        this.clientId = clientId;
        this.read(in);
    }

    //citire de la tastatura
    public void read(Scanner in) throws ParseException {
        in.nextLine();
        System.out.println("Name: ");
        this.name = in.nextLine();
        System.out.println("CNP: ");
        this.CNP = in.nextLine();
        System.out.println("Birth Date (yyyy-MM-dd): ");
        this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(in.nextLine());
        System.out.println("Email: ");
        this.email = in.nextLine();
        System.out.println("Phone: ");
        this.phone = in.nextLine();
        System.out.println("Address: ");
        this.address = new Address(in);
    }

    //constructor etapa 3
    public Client(int clientId, ResultSet in) throws SQLException {
        this.clientId = clientId;
        this.read(in);
    }

    public void read(ResultSet in) throws SQLException {
        this.name = in.getString("Name");
        this.CNP = in.getString("CNP");
        this.birthDate = in.getDate("birthDate");
        this.email = in.getString("email");
        this.phone = in.getString("phone");
        this.address = new Address(in);
    }

    //filtrarea tuturor conturilor dupa clientId-ul obiectului curent
    public List<Account> filterAccounts(List<Account> allAccounts) {
        ArrayList<Account> accounts = new ArrayList<Account>();
        for (Account account : allAccounts)
            if (account.getClientId() == this.getClientId())
                accounts.add(account);
        return accounts;
    }

    public List<Transaction> filterTransactions(List<Account> allAccounts, List<Transaction> allTransactions) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        List<Account> accounts = this.filterAccounts(allAccounts);
        for (Account account : accounts)
            transactions.addAll(account.filterTransactions(allTransactions));
        return transactions;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", name='" + name + '\'' +
                ", CNP='" + CNP + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                '}';
    }

    public String toCSV() {
        return clientId +
                "," + name +
                "," + CNP +
                "," + (new SimpleDateFormat("yyyy-MM-dd")).format(birthDate) +
                "," + email +
                "," + phone +
                "," + address.toCSV();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientId == client.clientId && name.equals(client.name) && CNP.equals(client.CNP) && birthDate.equals(client.birthDate) && email.equals(client.email) && phone.equals(client.phone) && address.equals(client.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, name, CNP, birthDate, email, phone, address);
    }

    public int getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public String getCNP() {
        return CNP;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}