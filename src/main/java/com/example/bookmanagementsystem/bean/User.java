package com.example.bookmanagementsystem.bean;

import lombok.Data;

@Data
public class User {
    private String account;

    private String password;

    private String nickname;

    private String telephone;

    private String email;

    public User() {}

    public User(String account, String password, String nickname, String telephone, String email) {
        this.account = account;
        this.password = password;
        this.nickname = nickname;
        this.telephone = telephone;
        this.email = email;
    }
}
