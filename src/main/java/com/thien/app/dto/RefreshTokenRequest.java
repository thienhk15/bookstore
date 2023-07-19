package com.thien.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;

}
