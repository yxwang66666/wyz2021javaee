package com.example.bookmanagementsystem.bean;

import lombok.Data;

@Data
public class Book {
    private String barCode;

    private String title;

    private String author;

    private String publisher;

    private String isbn;

    private Double price;

    private String introduction;

    private String image;

    private Boolean isBorrowed = false;

    public Book(String barCode, String title, String author, String publisher, String isbn, Double price, String introduction, String image) {
        this.barCode = barCode;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.price = price;
        this.introduction = introduction;
        this.image = image;
    }

    public Book() { }
}
