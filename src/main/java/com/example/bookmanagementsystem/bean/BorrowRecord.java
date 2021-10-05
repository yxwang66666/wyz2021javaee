package com.example.bookmanagementsystem.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

@Data
public class BorrowRecord {

    private String userAccount;

    private String bookBarCode;

    private String title;

    @Column(name = "borrowDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date borrowDate;

    private Integer days = 30;//可借阅天数

    public BorrowRecord(String userAccount, String bookBarCode,String title, Date borrowDate) {
        this.userAccount = userAccount;
        this.bookBarCode = bookBarCode;
        this.title = title;
        this.borrowDate = borrowDate;
    }
}
