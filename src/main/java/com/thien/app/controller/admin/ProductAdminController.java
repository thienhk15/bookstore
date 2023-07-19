package com.thien.app.controller.admin;

import com.thien.app.entity.Book;
import com.thien.app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductAdminController {
    @Autowired
    private BookService bookService;

    @GetMapping("")
    public String product(Model model){
        List<Book> bookList = bookService.getAllBooks();
        model.addAttribute("bookList", bookList);
        return "admin/table_product";
    }
    @GetMapping("/{bookId}")
    public String productDetail(@PathVariable Long bookId, Model model){
        Book book = bookService.getBookById(bookId);
        model.addAttribute("book", book);
        return "admin/product_detail";
    }
    @DeleteMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable Long bookId, Model model){
        Book book = bookService.deleteBookById(bookId);
        List<Book> bookList = bookService.getAllBooks();
        model.addAttribute("bookList", bookList);
        return "admin/table_product";
    }
    @PostMapping("/update")
    public String updateBook(@RequestBody Book book, Model model){
        Book updatedBook;
        try{
            updatedBook = bookService.updateBook(book);
        }catch (Exception e){
            return e.toString();
        }
        if(updatedBook==null){
            model.addAttribute("book", book);
        } else model.addAttribute("book", updatedBook);
        return "admin/product_detail";
    }
}
