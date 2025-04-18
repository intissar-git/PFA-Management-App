package com.example.demo.model;
import lombok.*;
import jakarta.persistence.*;

public class Livrable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String typeFichier;

    @Column(nullable = false)
    private String cheminFichier;
    @ManyToOne
    @JoinColumn(name = "tache_id", nullable = false)
    private Tache tache;

}
