package com.example.domain;

public class Book {

    private String id;
    private String isbn;
    private String title;
    private int edition;
    private String author;
    private String publisher;
    private double price;
    private String description;

    public Book() {
    }

    public Book(String id, String isbn, String title, int edition, String author, String publisher, double price, String description) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.edition = edition;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
