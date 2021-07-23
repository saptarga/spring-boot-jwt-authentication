package com.saptarga.demojwtauthentication.repository;

import com.saptarga.demojwtauthentication.entity.RefreshToken;
import com.saptarga.demojwtauthentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>  {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);
}
