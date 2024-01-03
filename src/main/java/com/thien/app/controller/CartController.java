package com.thien.app.controller;

import com.thien.app.dto.CartItemDto;
import com.thien.app.entity.CartItem;
import com.thien.app.entity.User;
import com.thien.app.service.BookService;
import com.thien.app.service.CartItemService;
import com.thien.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @GetMapping
    public ResponseEntity<?> getCartByUserId(@RequestParam Long userId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(email);
        List<CartItem> cartItemList = cartItemService.getCartItemByUserId(user.getId());

        //get list books
        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        for(int i=0;i<cartItemList.size();i++){
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setUserid(user.getId());
            cartItemDto.setBook(bookService.getBookById(cartItemList.get(i).getBookId()));
            cartItemDto.setQuantity(cartItemList.get(i).getQuantity());
            cartItemDtoList.add(cartItemDto);
        }
        return ResponseEntity.ok(cartItemDtoList);
    }
//    @PostMapping("/update")
//    public ResponseEntity<?> updateCartItem(@RequestParam Long userId,
//                                            @RequestParam Long bookId,
//                                            @RequestParam Long quantity
//    ){
//        CartItem cartItem = new CartItem(userId, bookId, quantity);
//        cartItemService.updateCartItem(cartItem);
//        return ResponseEntity.ok(cartItem);
//    }
//    @PostMapping("/create")
//    public ResponseEntity<?> createCartItem(@RequestParam Long userId,
//                                            @RequestParam Long bookId,
//                                            @RequestParam Long quantity
//    ){
//        CartItem cartItem = new CartItem(userId, bookId, quantity);
//        cartItemService.createCartItem(cartItem);
//        return ResponseEntity.ok(cartItem);
//    }
}
