package com.example.DB;

import com.example.domain.Book;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 模拟数据库数据。
 */
public class DB {
    private static Map<String, Book> books = new LinkedHashMap<>(); // 需要保留顺序，所以使用 LinkedHashMap

    static {
        books.put("1",
                new Book("1",
                        "978-0134685991",
                        "Effective Java",
                        3,
                        "Joshua Bloch",
                        "Addison-Wesley Professional; 3 edition (January 6, 2018)",
                        27.94,
                        "The Definitive Guide to Java Platform Best Practices–Updated for Java 7, 8, and 9"));
        books.put("2",
                new Book("2",
                        "978-0596009205",
                        "Head First Java",
                        2,
                        "Kathy Sierra， Bert Bates",
                        "O'Reilly Media; 2nd edition (February 19, 2005)",
                        30.30,
                        "Learning a complex new language is no easy task especially when it s an object-oriented computer programming language like Java. You might think the problem is your brain. It seems to have a mind of its own, a mind that doesn't always want to take in the dry, technical stuff you're forced to study."));
        books.put("3",
                new Book("3",
                        "978-0596007126",
                        "Head First Design Patterns: A Brain-Friendly Guide",
                        1,
                        "Eric Freeman, Elisabeth Robson, Bert Bates, Kathy Sierra",
                        "O'Reilly Media; 1st edition (October 2004)",
                        46.15,
                        "At any given moment, someone struggles with the same software design problems you have. And, chances are, someone else has already solved your problem. This edition of Head First Design Patterns—now updated for Java 8—shows you the tried-and-true, road-tested patterns used by developers to create functional, elegant, reusable, and flexible software. By the time you finish this book, you’ll be able to take advantage of the best design practices and experiences of those who have fought the beast of software design and triumphed."));
        books.put("4",
                new Book("4",
                        "978-0131872486",
                        "Thinking in Java (4th Edition)",
                        1,
                        "Bruce Eckel",
                        "Prentice Hall; 4 edition (February 20, 2006)",
                        35.26,
                        "“Thinking in Java should be read cover to cover by every Java programmer, then kept close at hand for frequent reference. The exercises are challenging, and the chapter on Collections is superb! Not only did this book help me to pass the Sun Certified Java Programmer exam; it’s also the first book I turn to whenever I have a Java question."));
    }

    public static Map<String, Book> getBooks() {
        return books;
    }
}
