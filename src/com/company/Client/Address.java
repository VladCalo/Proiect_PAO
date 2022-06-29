package com.company.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Address {
    private String street;
    private String city;
    private String county;
    private int postalCode;

    //constructor etapa 1
    public Address(String street, String city, String county, int postalCode) {
        this.street = street;
        this.city = city;
        this.county = county;
        this.postalCode = postalCode;
    }

    //construcotr cu citire de la tastatura
    public Address(Scanner in) {
        this.read(in);
    }

    //citire de la tastatura
    public void read(Scanner in) {
        System.out.println("Street: ");
        this.street = in.nextLine();
        System.out.println("City: ");
        this.city = in.nextLine();
        System.out.println("County: ");
        this.county = in.nextLine();
        System.out.println("Postal code: ");
        this.postalCode = Integer.parseInt(in.nextLine());
    }

    //constructor pentru etapa 3
    public Address(ResultSet in) throws SQLException {
        this.read(in);
    }

    public void read(ResultSet in) throws SQLException {
        this.street = in.getString("street");
        this.city = in.getString("city");
        this.county = in.getString("county");
        this.postalCode = in.getInt("postalCode");
    }


    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", postalCode=" + postalCode +
                '}';
    }

    public String toCSV() {
        return street +
                "," + city +
                "," + county +
                "," + postalCode;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public int getPostalCode() {
        return postalCode;
    }


}
