package com.thien.app.service;

import com.thien.app.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(long id);
    Book createBook(Book book);
    Book updateBook(Book book);
    Book deleteBookById(long id);
}
