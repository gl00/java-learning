package com.example.dao;

import com.example.DB.DB;
import com.example.domain.Book;

import java.util.Map;

public class BookDao {

    public Map<String, Book> getAll() {
        return DB.getBooks();
    }

    public Book find(String id) {
        return DB.getBooks().get(id);
    }
}
