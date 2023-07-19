package com.thien.app.repository;

import com.thien.app.entity.CartItem;
import com.thien.app.entity.key.CartItemKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemKey> {

    Optional<CartItem> findById(CartItemKey cartItemKey);

    // Native SQL    @Query(value = "select * from cart_items c where c.userId = :userId ", nativeQuery = true)
// JPQL   @Query( "select c from cart_items where c.userId = :userId ", nativeQuery = true)
    List<CartItem> findByUserId(long userId);
    void deleteById(CartItemKey cartItemKey);
    void deleteByUserId(long userId);
}
