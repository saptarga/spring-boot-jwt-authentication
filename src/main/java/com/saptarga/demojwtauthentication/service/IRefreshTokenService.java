package com.saptarga.demojwtauthentication.service;

import com.saptarga.demojwtauthentication.entity.RefreshToken;

import java.util.Optional;

public interface IRefreshTokenService {

    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyExpiration(RefreshToken token);

    int deleteByUserId(Long userId);
}
