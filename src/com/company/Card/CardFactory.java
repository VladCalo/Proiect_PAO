package com.company.Card;

public class CardFactory {
    private static int id = 0;

    public Card addCard(String IBAN, String name) {
        return new Card(id++, IBAN, name);
    }

    public MasterCard createMasterCard(String IBAN, String name) {
        return new MasterCard(id++, IBAN, name);
    }

    public Visa createVisaCard(String IBAN, String name) {
        return new Visa(id++, IBAN, name);
    }
}
