package com.thien.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Invalid email")
    private String email;
    @Length(min=6, max=32, message="Password's length = 6 -> 32")
    private String password;
    private String image;
    @NotEmpty(message = "Name must not be empty")
    private String name;
    private Date birthday;
    private String phone;
}
