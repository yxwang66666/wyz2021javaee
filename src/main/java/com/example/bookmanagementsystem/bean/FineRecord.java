package com.example.bookmanagementsystem.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class FineRecord {
    // 罚款日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date fineDate;

    // 罚款金额
    private int amount;

    // 用户
    private String userAccount;

    public FineRecord(Date fineDate, int amount, String userAccount) {
        this.fineDate = fineDate;
        this.amount = amount;
        this.userAccount = userAccount;
    }
}
