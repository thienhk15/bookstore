package com.thien.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    private Long userId;
    private Long orderId;
    private Long bookId;
    private Long quantity;
}
