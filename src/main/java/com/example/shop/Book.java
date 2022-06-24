package com.example.shop;

import java.math.BigDecimal;

public class Book {
    private String name;
    private String author;
    private BigDecimal price;
    private int quantity;
    private int id;


    public Book(String name, String author, BigDecimal price, int quantity) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public Book(int id, String name, String author, BigDecimal price, int quantity) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.id = id;
    }

    public Book(int id, String name, String author, BigDecimal price) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.id = id;
    }

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
