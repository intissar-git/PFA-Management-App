package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private boolean expired;

    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Utilisateur utilisateur;

    private Instant expirationTime;

    // Méthode builder statique
    public static TokenBuilder builder() {
        return new TokenBuilder();
    }

    // Classe Builder interne
    public static class TokenBuilder {
        private Long id;
        private String token;
        private boolean expired;
        private boolean revoked;
        private Utilisateur utilisateur;
        private Instant expirationTime;

        public TokenBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TokenBuilder token(String token) {
            this.token = token;
            return this;
        }

        public TokenBuilder expired(boolean expired) {
            this.expired = expired;
            return this;
        }

        public TokenBuilder revoked(boolean revoked) {
            this.revoked = revoked;
            return this;
        }

        public TokenBuilder utilisateur(Utilisateur utilisateur) {
            this.utilisateur = utilisateur;
            return this;
        }

        public TokenBuilder expirationTime(Instant expirationTime) {
            this.expirationTime = expirationTime;
            return this;
        }

        public Token build() {
            Token tokenObj = new Token();
            tokenObj.setId(this.id);
            tokenObj.setToken(this.token);
            tokenObj.setExpired(this.expired);
            tokenObj.setRevoked(this.revoked);
            tokenObj.setUtilisateur(this.utilisateur);
            tokenObj.setExpirationTime(this.expirationTime);
            return tokenObj;
        }
    }
}