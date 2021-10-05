package com.example.bookmanagementsystem.bean;

import lombok.Data;

@Data
public class BookKind {
    private String title;

    private String author;

    private String publisher;

    private String isbn;

    private Float price;

    private String introduction;

    private String image;

    private Integer num;

    private Integer sum;

    public BookKind(String title, String author, String publisher, String isbn, Float price, String introduction, String image, Integer num, Integer sum) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.price = price;
        this.introduction = introduction;
        this.image = image;
        this.num = num;
        this.sum = sum;
    }
}
