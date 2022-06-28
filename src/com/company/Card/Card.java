package com.company.Card;

import java.util.*;

public class Card {
    private final int cardId;
    private final int CVV;
    private String number;
    private String name;
    private String IBAN;
    private final Date expirationDate;

    static private final Set<String> usedNumbers = new HashSet<>();

    public Card(int cardId, String IBAN, String name) {
        this.cardId = cardId;
        this.IBAN = IBAN;
        this.name = name;
        this.number = this.generateCardNumber();

        while (usedNumbers.contains(this.number))
            this.number = this.generateCardNumber();
        usedNumbers.add(this.number);

        this.CVV = this.generateCardCVV();

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 3);
        this.expirationDate = c.getTime();
    }

    public void read(Scanner in) {
        System.out.println("IBAN: ");
        this.IBAN = in.nextLine();
        System.out.println("Name: ");
        this.name = in.nextLine();
    }

    private String generateCardNumber() {
        String number = "";
        Random random = new Random();
        int nr = random.nextInt(10000);
        number = number + nr;
        for (int i = 0; i < 3; i++) {
            nr = random.nextInt(10000);
            number = number + " " + nr;
        }
        return number;
    }

    private int generateCardCVV() {
        Random rand = new Random();
        return rand.nextInt(1000);
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", CVV=" + CVV +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", IBAN='" + IBAN + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardId == card.cardId && CVV == card.CVV && number.equals(card.number) && name.equals(card.name) && IBAN.equals(card.IBAN) && expirationDate.equals(card.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId, CVV, number, name, IBAN, expirationDate);
    }

    public int getCardId() {
        return cardId;
    }

    public int getCVV() {
        return CVV;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getIBAN() {
        return IBAN;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }
}
