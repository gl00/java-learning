package com.example.domain;

/**
 * 购物项，代表某个商品，以及商品出现的次数。
 */
public class CartItem {
    private Book book;
    private int quantity;
    private double cost;

    public CartItem() {
    }

    public CartItem(Book book, int quantity, double cost) {
        this.book = book;
        this.quantity = quantity;
        this.cost = cost;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        cost = quantity * book.getPrice();
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
