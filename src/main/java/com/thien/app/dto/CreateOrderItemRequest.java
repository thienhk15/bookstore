package com.thien.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderItemRequest {
    private Long bookId;
    private Long price;
    private Long quantity;

    // Constructors, getters, setters...
}
