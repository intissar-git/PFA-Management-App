package com.example.demo.model;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

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


    // Ajoutez cette relation
    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL)
    private List<Etudiant> etudiants;

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }
}