package com.example.demo.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)  // Stratégie JOINED
public class Encadrant extends Utilisateur {

    @Column(name = "adresse_email", unique = true, nullable = false)
    private String adresseEmail;
    @Column(name = "specialite", nullable = false)
    private String specialite;
    @ManyToOne
    @JoinColumn(name = "departement_id", nullable = false)
    private Departement departement;


}
