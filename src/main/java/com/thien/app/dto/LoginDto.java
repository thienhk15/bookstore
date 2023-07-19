package com.thien.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {
    @NotNull(message = "Email không được null")
    @NotBlank(message = "Email không được trống")
    private String email;

    @NotNull(message = "Mật khẩu không được null")
    @NotBlank(message = "Mật khẩu không được trống")
    private String password;
}
