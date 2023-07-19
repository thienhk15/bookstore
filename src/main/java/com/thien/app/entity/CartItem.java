package com.thien.app.entity;

import com.thien.app.entity.key.CartItemKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart_items")
@IdClass(CartItemKey.class)
public class CartItem {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Id
    @Column(name = "book_id")
    private Long bookId;
    @Column(nullable = false)
    private Long quantity;

    public CartItem(Long userId, Long bookId, Long quantity){
        this.userId = userId;
        this.bookId = bookId;
        this. quantity = quantity;
    }

}
