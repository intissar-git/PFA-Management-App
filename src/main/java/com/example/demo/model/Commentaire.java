package com.example.demo.model;
import lombok.*;
import jakarta.persistence.*;

public class Commentaire {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, columnDefinition = "TEXT")
        private String contenu;

        @ManyToOne
        @JoinColumn(name = "livrable_id", nullable = false)
        private Livrable livrable;

        @ManyToOne
        @JoinColumn(name = "utilisateur_id", nullable = false)
        private Utilisateur utilisateur;

}
