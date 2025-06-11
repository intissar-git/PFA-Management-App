package com.example.demo.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Groupe {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String intitule;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "encadrant_id", nullable = false)
    private Encadrant encadrant;

    @ManyToOne
    @JoinColumn(name = "filiere_id", nullable = false)
    private Filiere filiere;
}