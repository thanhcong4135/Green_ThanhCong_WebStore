package com.green.auth.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.auth.domain.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByToken(String token);
    void deleteByUsername(String username);
}
