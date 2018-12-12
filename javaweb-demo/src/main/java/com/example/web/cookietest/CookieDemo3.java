package com.example.web.cookietest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 显示上次浏览过的商品的实现过程.
 * <p>
 * CookieDemo3 显示图书列表。
 * <p>
 * 浏览过的商品。
 */
@WebServlet(name = "CookieDemo3", value = "/CookieDemo3")
public class CookieDemo3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        // 输出网站所有商品
        out.write("本站有如下商品：<br>");
        Map<String, Book> books = Db.getBooks();
        Set<Map.Entry<String, Book>> entries = books.entrySet();
        for (Map.Entry<String, Book> bookEntry : entries) {
            String k = bookEntry.getKey();
            Book v = bookEntry.getValue();
            out.write("<a href='/javaweb-demo/CookieDemo4?id=" + v.getId() + "'>" + v.getName() + "</a><br>");
        }

        // 显示用户曾今看过的商品
        out.write("<br>您看过的商品：<br>");
        Cookie[] cookies = req.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if ("bookHistory".equals(cookies[i].getName())) {
                String value = cookies[i].getValue();
                String[] ids = value.split("\\|");
                for (String id : ids) {
                    Book book = books.get(id);
                    out.write(book.getName() + "<br>");
                }
            }
        }
    }
}

class Db {
    private static Map<String, Book> books = new LinkedHashMap<>();

    static {
        books.put("1",
                new Book("1",
                        "The C Programming Language",
                        "Brian Kernighan · Dennis Ritchie",
                        "C 经典"));
        books.put("2",
                new Book("2",
                        "Thinking in Java",
                        "Bruce Eckel",
                        "Thinking in Java is a book about the Java programming language, written by Bruce Eckel and first published in 1998. Prentice Hall published the 4th edition of the work in 2006. The book represents a print version of Eckel’s “Hands-on Java” seminar."));
        books.put("3",
                new Book("3",
                        "Core Java",
                        "Cay S. Horstmann",
                        "The book you have in your hands is the first volume of the tenth edition of Core Java®."));
        books.put("4",
                new Book("4",
                        "C Primer Plus",
                        "Stephen Prata",
                        "C Primer Plus is a carefully tested, well-crafted, and complete tutorial on a subject core to programmers and developers. This computer science classic teaches principles of programming, including structured code and top-down design."));
    }

    public static Map<String, Book> getBooks() {
        return books;
    }
}

class Book {
    private String id;
    private String name;
    private String author;
    private String description;

    public Book() {
    }

    public Book(String id, String name, String author, String description) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(name, book.name) &&
                Objects.equals(author, book.author) &&
                Objects.equals(description, book.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, description);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
