package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private int capacite;

    @ManyToOne
    @JoinColumn(name = "bloc_id")
    private Bloc bloc;
}