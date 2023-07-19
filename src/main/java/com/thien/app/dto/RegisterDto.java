package com.thien.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@AllArgsConstructor
@Data
public class RegisterDto {
    @NotNull(message = "tên không được null")
    @NotBlank(message = "tên không được trống")
    private String name;
    @NotNull(message = "Email không được null")
    @NotBlank(message = "Email không được trống")
    private String email;
    @NotNull(message = "mk không được null")
    @NotBlank(message = "mk không được trống")
    private String password;
    private Date birthday;
    private String phone;
}
