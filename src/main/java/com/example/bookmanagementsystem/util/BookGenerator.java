package com.example.bookmanagementsystem.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BookGenerator {
    public static String url = "jdbc:mysql://localhost:3306/bms_schema?setUnicode=true&characterEncoding=utf8";

    public static void insertCSV(String filepath) throws SQLException, ClassNotFoundException, IOException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, "root", "123456");
        Statement statement = connection.createStatement();

        FileReader reader = new FileReader(filepath);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(reader);
        for (CSVRecord record : records) {
            boolean legal = true;
            for (int i = 0; i <= 5; i++) {
                if (record.get(i).equals("") || record.get(i).contains("'") || record.get(i).length() > 1500) {
                    legal = false;
                    break;
                }
            }
            if (legal) {
                statement.execute("insert into book(isbn, title, author, publisher, price, introduction, image)" +
                                       "values (" + "'" + record.get(0) + "'," +
                                                    "'" + record.get(1) + "'," +
                                                    "'" + record.get(2) + "'," +
                                                    "'" + record.get(3) + "'," +
                                                          record.get(4) + ","  +
                                                    "'" + record.get(5) + "'," +
                                                    "'" + record.get(6) + "')");
                System.out.printf("succeed in inserting book %s(isbn: %s)%n", record.get(1), record.get(0));
            }
        }

        if (statement != null)
            statement.close();
        connection.close();
    }

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        insertCSV("F:\\intelliJ IDEA\\MyCode\\BookManagementSystem\\src\\main\\resources\\file\\book.csv");
    }
}
