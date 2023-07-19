package com.thien.app.entity;

import lombok.*;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private Map<String, String> errors;

    // constructors, getters, setters
}
