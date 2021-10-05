package com.example.bookmanagementsystem.util;

import com.example.bookmanagementsystem.bean.Administrator;

import java.sql.*;

public class AdministratorGenerator {
    public static String url = "jdbc:mysql://localhost:3306/bms_schema?setUnicode=true&characterEncoding=utf8";

    public static void insert(Administrator administrator) throws SQLException, ClassNotFoundException {
        Encoder.encode(administrator);

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, "root", "123456");
        Statement statement = connection.createStatement();
        Boolean success = statement.execute(
                "insert into administrator(account, password, nickname, telephone, email) " +
                        "values (" + "'" + administrator.getAccount() + "'," +
                                 "'" + administrator.getPassword() + "'," +
                                 "'" + administrator.getNickname() + "'," +
                                 "'" + administrator.getTelephone() + "'," +
                                 "'" + administrator.getEmail() + "')");

        if (statement != null)
            statement.close();
        if (connection != null)
            connection.close();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String account = "000000";
        String password = "a000000";
        String nickname = "wwk";
        String telephone = "18350019147";
        String email = "wwk@wwk.com";

        insert(new Administrator(account, password, nickname, telephone, email));
    }
}
