package com.example.demo.repository;

import com.example.demo.model.Token;
import com.example.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    @Query("""
        SELECT t FROM Token t
        WHERE t.utilisateur.id = :userId
        AND t.expired = false
        AND t.revoked = false
        AND t.expirationTime > CURRENT_TIMESTAMP
    """)
    List<Token> findAllValidTokensByUser(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE Token t SET t.expired = true, t.revoked = true WHERE t.utilisateur.id = :userId")
    void invalidateAllUserTokens(Long userId);
}