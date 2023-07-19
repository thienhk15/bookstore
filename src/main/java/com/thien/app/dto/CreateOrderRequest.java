package com.thien.app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    @NotNull
    private Long userId;
    @NotNull
    private String phone;
    @NotNull
    private String address;
    @NotNull
    private String price;
    private List<CreateOrderItemRequest> items;

    // Constructors, getters, setters...
}

