package com.company.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class ClientFactory {
    private static int id = 0;

    public static void incrementUniqueId(int i) {
        ClientFactory.id += i;
    }

    public Client createClient(Scanner in) throws ParseException {
        return new Client(id++, in);
    }
    public Client createClient(ResultSet in) throws SQLException {
        return new Client(id++, in);
    }
}
