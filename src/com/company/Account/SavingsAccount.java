package com.company.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class SavingsAccount extends Account{
    private final Date startDate;
    private final Date endDate;
    private final int interest;

    //constructor folosir in etapa 1
    public SavingsAccount(String name, int clientId, int id) {
        super(name, clientId, id);

        this.startDate = new Date();
        this.interest = 3;

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 1);
        this.endDate = c.getTime();
    }

    //constructor folosir in etapa 2
    public SavingsAccount(String IBAN, String swift, double amount, String name, int clientId, Date startDate, Date endDate, int interest) {
        super(IBAN, swift, amount, name, clientId);
        this.startDate = startDate;
        this.endDate = endDate;
        this.interest = interest;
    }

    //constructor folosir in etapa 3
    public SavingsAccount(ResultSet in) throws SQLException {
        super(in);
        this.startDate = in.getDate("startDate");
        this.endDate = in.getDate("endDate");
        this.interest = in.getInt("interest");
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "IBAN='" + IBAN + '\'' +
                ", swift='" + swift + '\'' +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                ", clientId=" + clientId +
                ", cards=" + cards +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", interest=" + interest +
                '}';
    }

    public String toCSV() {
        return IBAN +
                "," + swift +
                "," + amount +
                "," + name +
                "," + clientId +
                "," + cards +
                "," + (new SimpleDateFormat("yyyy-MM-dd")).format(startDate) +
                "," + (new SimpleDateFormat("yyyy-MM-dd")).format(endDate) +
                "," + interest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SavingsAccount that = (SavingsAccount) o;
        return interest == that.interest && startDate.equals(that.startDate) && endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startDate, endDate, interest);
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getInterest() {
        return interest;
    }


}
