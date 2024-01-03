package com.thien.app.controllerrr.customer;

import com.thien.app.entity.Book;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/customer/products")
public class ProductController {
    private final RestTemplate restTemplate;

    public ProductController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("")
    public ModelAndView product(Model model){

        String apiUrl = "http://localhost:8080/api/books";
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {}
        );
        //get list books
        List<Book> bookList = response.getBody();
        model.addAttribute("bookList", bookList);
        return new ModelAndView("shop.html", model.asMap());
    }
    @GetMapping("/{bookId}")
    public ModelAndView productDetail(@PathVariable Long bookId, Model model){

        String apiUrl = "http://localhost:8080/api/books/" + bookId;
        Book book = restTemplate.getForObject(apiUrl, Book.class);
        model.addAttribute("book", book);
        return new ModelAndView("product-details.html", model.asMap());
    }
}
