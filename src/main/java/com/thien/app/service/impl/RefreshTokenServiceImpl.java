package com.thien.app.service.impl;

import com.thien.app.entity.RefreshToken;
import com.thien.app.repository.RefreshTokenRepository;
import com.thien.app.repository.UserRepository;
import com.thien.app.security.exception.TokenRefreshException;
import com.thien.app.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Value("${app.jwt-refresh-expiration-ms}")
    private Long refreshTokenDurationMs;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(Long userId, String token, Date expiredDate) {
        RefreshToken refreshToken = new RefreshToken(userId, token, expiredDate);
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public boolean verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiredDate().compareTo(Date.from(Instant.now())) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh token was expired");
        }
        return true;
    }

    @Override
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUserId(userRepository.findById(userId).get().getId());
    }
}
