package com.example.domain;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Cart {

    private Map<String, CartItem> map = new LinkedHashMap<>();
    private double total;

    public void add(Book book) {
        CartItem item = map.get(book.getId());
        if (item == null) {
            item = new CartItem(book, 1, book.getPrice());
            map.put(book.getId(), item);
        } else {
            item.setQuantity(item.getQuantity() + 1);
        }
    }

    public Map<String, CartItem> getMap() {
        return map;
    }

    public void setMap(Map<String, CartItem> map) {
        this.map = map;
    }

    public double getTotal() {
        double totalPrice = 0;
        Set<Map.Entry<String, CartItem>> entries = map.entrySet();
        for (Map.Entry<String, CartItem> entry : entries) {
            CartItem item = entry.getValue();
            totalPrice += item.getCost();
        }
        total = totalPrice;
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
