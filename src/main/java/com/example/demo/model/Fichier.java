package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import lombok.Builder;
@Builder

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Fichier {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "chemin", nullable = false)
    private String chemin;
    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;
    @Column(name = "nom", nullable = false)
    private String nom;
    @Column(name = "taille", nullable = false)
    private Long taille;

    public String getType() {
        return type;
    }

    @Column(name = "type", nullable = false)
    private String type;
    @OneToOne
    @JoinColumn(name = "livrable_id")
    @JsonBackReference
    private Livrable livrable;


}


