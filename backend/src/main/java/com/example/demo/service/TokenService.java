package com.example.demo.service;

import com.example.demo.model.Token;
import com.example.demo.model.Utilisateur;
import com.example.demo.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public void saveUserToken(Utilisateur utilisateur, String jwtToken) {
        Token token = Token.builder()
                .token(jwtToken)
                .utilisateur(utilisateur)
                .expired(false)
                .revoked(false)
                .expirationTime(Instant.now().plus(24, ChronoUnit.HOURS))
                .build();
        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(Utilisateur utilisateur) {
        List<Token> validTokens = tokenRepository.findAllValidTokensByUser(utilisateur.getId());
        if (validTokens.isEmpty()) return;

        validTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validTokens);
        // Alternative plus performante:
        // tokenRepository.invalidateAllUserTokens(utilisateur.getId());
    }

    public boolean isTokenValid(String jwtToken) {
        return tokenRepository.findByToken(jwtToken)
                .map(token -> !token.isExpired() && !token.isRevoked())
                .orElse(false);
    }
}