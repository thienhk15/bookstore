package com.thien.app.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class JWTAuthResponse {
    private String accessToken;
    private static String tokenType = "Bearer";
    private String refreshToken;
    private Long userId;
    private String email;
    private String status;

    public JWTAuthResponse( String accessToken, String refreshToken, Long userId, String email, String status) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.email = email;
        this.status = status;
    }

    public JWTAuthResponse(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return tokenType + " " + accessToken + " " + refreshToken;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(new TokenResponse(accessToken, refreshToken));
    }
    class TokenResponse {
        private String accessToken;
        private String refreshToken;
        public TokenResponse(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
