package com.example.bookmanagementsystem.bean;

import lombok.Data;

@Data
public class Administrator extends User {
    public Administrator(String account, String password, String nickname, String telephone, String email) {
        super(account, password, nickname, telephone, email);
    }
}
