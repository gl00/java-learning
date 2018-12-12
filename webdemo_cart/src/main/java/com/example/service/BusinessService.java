package com.example.service;

import com.example.dao.BookDao;
import com.example.domain.Book;
import com.example.domain.Cart;

import java.util.Map;

/**
 * 业务类。统一对 web 层提供所有服务。
 */
public class BusinessService {

    private BookDao dao = new BookDao();

    public Map<String, Book> getAllBooks() {
        return dao.getAll();
    }

    public Book findBook(String id) {
        return dao.find(id);
    }

    public void deleteCartItem(Cart cart, String id) {
        cart.getMap().remove(id);
    }

    public void clearCart(Cart cart) {
        cart.getMap().clear();
    }

    public void changeQuantity(Cart cart, String id, int quantity) {
        cart.getMap().get(id).setQuantity(quantity);
    }
}
