package com.thien.app.service;

import com.thien.app.entity.RefreshToken;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.Optional;

public interface RefreshTokenService {
    public Optional<RefreshToken> findByToken(String token);
    public RefreshToken createRefreshToken(Long userId, String token, Date expiredDate);

    public boolean verifyExpiration(RefreshToken token) throws Exception;
    @Transactional
    public int deleteByUserId(Long userId);
}
