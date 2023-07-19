package com.thien.app.entity.key;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CartItemKey implements Serializable {
    private Long userId;
    private Long bookId;
}
