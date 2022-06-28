package com.company.Transaction;

import java.util.Comparator;

public class CompTransaction implements Comparator<Transaction> {
    @Override
    public int compare(Transaction o1, Transaction o2) {
        return o1.getCreationDate().compareTo(o2.getCreationDate());
    }
}

