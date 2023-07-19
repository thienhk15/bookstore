package com.thien.app.service;

import com.thien.app.entity.CartItem;
import com.thien.app.entity.key.CartItemKey;
import com.thien.app.repository.CartItemRepository;

import java.util.List;
public interface CartItemService {
    List<CartItem> getCartItemByUserId(Long userId);
    CartItem createCartItem(CartItem cartItem);
    CartItem updateCartItem(CartItem cartItem);
    void deleteCartItem(CartItemKey cartItemKey);
    List<CartItem> deleteCartByUserId(long userId);
}
