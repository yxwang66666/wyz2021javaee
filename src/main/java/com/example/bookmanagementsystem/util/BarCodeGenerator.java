package com.example.bookmanagementsystem.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.Random;

public class BarCodeGenerator {
    public static String url = "jdbc:mysql://localhost:3306/bms_schema?setUnicode=true&characterEncoding=utf8";

    public static void insert(int startBarCode, int minNum, int maxNum) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, "root", "123456");
        Statement statementQuery = connection.createStatement();
        Statement statementInsert = connection.createStatement();
        ResultSet resultSet = statementQuery.executeQuery("select isbn from book");

        Random random = new Random();
        int currentBarCode = startBarCode;
        while (resultSet.next()) {
            String isbn = resultSet.getString("ISBN");
            int countBook = random.nextInt(maxNum - minNum + 1) + minNum;
            for (int j = 0; j < countBook; j++) {
                statementInsert.execute("insert into barCode(barCode, ISBN)" +
                        "values (" + "'" + String.format("%013d", currentBarCode) + "'," +
                        "'" + isbn + "')");
                currentBarCode++;
            }
            System.out.println("succeed in inserting barCode " + isbn + ", a total of " + countBook);
        }

        statementQuery.close();
        statementInsert.close();
        connection.close();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        insert(0,3,8);
    }
}
