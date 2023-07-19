package com.thien.app.service.impl;

import com.thien.app.entity.Book;
import com.thien.app.entity.CartItem;
import com.thien.app.entity.User;
import com.thien.app.entity.key.CartItemKey;
import com.thien.app.repository.BookRepository;
import com.thien.app.repository.CartItemRepository;
import com.thien.app.repository.UserRepository;
import com.thien.app.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<CartItem> getCartItemByUserId(Long userId) {
        List<CartItem> cartItemList = cartItemRepository.findByUserId(userId);
        return cartItemList;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        CartItemKey cartItemKey = new CartItemKey(cartItem.getUserId(), cartItem.getBookId());
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemKey);
        if(optionalCartItem.isPresent()) {
            throw  new IllegalArgumentException("CartItem exists with userId: "+ cartItem.getUserId() + " and bookId: "+ cartItem.getBookId());
        }
        else {
            Optional<Book> optionalBook = bookRepository.findById(cartItem.getBookId());
            if(optionalBook.isEmpty()){
                throw new IllegalArgumentException("Something went wrong!");
            }
            Optional<User> optionalUser = userRepository.findById(cartItem.getUserId());
            if(optionalUser.isEmpty()){
                throw new IllegalArgumentException("Something went wrong!");
            }
            return cartItemRepository.save(cartItem);
        }
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        CartItemKey cartItemKey = new CartItemKey(cartItem.getUserId(), cartItem.getBookId());
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemKey);
        if(optionalCartItem.isPresent()) {
            if(cartItem.getQuantity()==0) {
                deleteCartItem(cartItemKey);
                return cartItem;
            }
            else {
                return cartItemRepository.save(cartItem);
            }
        }
        else {
            throw new IllegalArgumentException("CartItem not found with userId: "+ cartItem.getUserId() + " and bookId: "+ cartItem.getBookId());
        }
    }

    @Override
    public void deleteCartItem(CartItemKey cartItemKey) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemKey);
        if(optionalCartItem.isPresent()) {
            cartItemRepository.deleteById(cartItemKey);
        }
        else {
            throw  new IllegalArgumentException("CartItem not found with userId: "+ cartItemKey.getUserId() + " and bookId: "+ cartItemKey.getBookId());
        }
    }

    @Override
    public List<CartItem> deleteCartByUserId(long userId) {
        List<CartItem> cartItemList = getCartItemByUserId(userId);
        cartItemRepository.deleteByUserId(userId);
        return cartItemList;
    }
}
