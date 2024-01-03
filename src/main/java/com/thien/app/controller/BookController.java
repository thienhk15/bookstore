package com.thien.app.controller;

import com.thien.app.entity.Book;
import com.thien.app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable(name = "bookId") Long id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<Book> deleteBookById(@PathVariable Long bookId) {
        Book deletedBook = bookService.deleteBookById(bookId);
        if (deletedBook != null) {
            return ResponseEntity.ok(deletedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Book> createBook(@RequestParam("title") String title,
                                           @RequestParam String description,
                                           @RequestParam String releaseYear,
                                           @RequestParam(required = false) String language,
                                           @RequestParam(required = false) String image,
                                           @RequestParam Long price,
                                           @RequestParam(required = false) Long categoryId,
                                           @RequestParam(required = false) Long authorId,
                                           @RequestParam(required = false) Long publisherId
    ) {
        Book newBook = new Book(title, description, releaseYear, price);
        Book createdBook = bookService.createBook(newBook);
        return ResponseEntity.ok(createdBook);
    }
    @PostMapping("/update")
    public ResponseEntity<Book> updateBook(@RequestParam Long id,
                                           @RequestParam(name = "title", required = false) String title,
                                           @RequestParam(required = false) String description,
                                           @RequestParam(required = false) String releaseYear,
                                           @RequestParam(required = false) String language,
                                           @RequestParam(required = false) String image,
                                           @RequestParam(required = false) Long price,
                                           @RequestParam(required = false) Long categoryId,
                                           @RequestParam(required = false) Long authorId,
                                           @RequestParam(required = false) Long publisherId
    ){

        Book book = bookService.getBookById(id);
        if(book==null){
            return ResponseEntity.notFound().build();
        }

        if (title != null) {
            book.setTitle(title);
        }
        if (description != null) {
            book.setDescription(description);
        }
        if (releaseYear != null) {
            book.setReleaseYear(releaseYear);
        }
        if (language != null) {
            book.setLanguage(language);
        }
        if (image != null) {
            book.setImage(image);
        }
        if (price != null) {
            book.setPrice(price);
        }
        if (categoryId != null) {
            book.setCategoryId(categoryId);
        }
        if (authorId != null) {
            book.setAuthorId(authorId);
        }
        if (publisherId != null) {
            book.setPublisherId(publisherId);
        }
        Book updatedBook = bookService.updateBook(book);
        return ResponseEntity.ok(updatedBook);
    }
}
