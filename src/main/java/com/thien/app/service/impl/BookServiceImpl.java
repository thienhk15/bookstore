package com.thien.app.service.impl;

import com.thien.app.entity.Book;
import com.thien.app.repository.BookRepository;
import com.thien.app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Override
    public List<Book> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList;
    }

    @Override
    public Book getBookById(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent()){
            return optionalBook.get();
        }
        else{
            throw new IllegalArgumentException("Book not found with ID: " + id);
        }

    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        if(optionalBook.isPresent()){
            return bookRepository.save(book);
        }
        else {
            throw new IllegalArgumentException("Book not found with id: "+ book.getId());
        }
    }

    @Override
    public Book deleteBookById(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent()){
            Book book = optionalBook.get();
            bookRepository.deleteById(id);
            return book;
        }
        else {
            throw new IllegalArgumentException("Book not found with ID: " + id);
        }
    }
}
