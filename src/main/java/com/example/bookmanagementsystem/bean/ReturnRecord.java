package com.example.bookmanagementsystem.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ReturnRecord {
    private String userAccount;

    private String bookBarCode;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date borrowDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date returnDate;

    public ReturnRecord(String userAccount, String bookBarCode, String title, Date borrowDate, Date returnDate) {
        this.userAccount = userAccount;
        this.bookBarCode = bookBarCode;
        this.title = title;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
}
