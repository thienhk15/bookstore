package com.thien.app.service;

import com.thien.app.entity.RefreshToken;
import jakarta.transaction.Transactional;

import java.util.Optional;

public interface RefreshTokenService {
    public Optional<RefreshToken> findByToken(String token);
    public RefreshToken createRefreshToken(Long userId);
    public RefreshToken verifyExpiration(RefreshToken token) throws Exception;
    @Transactional
    public int deleteByUserId(Long userId);
}
