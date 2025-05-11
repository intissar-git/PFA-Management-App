package com.example.demo.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)  // Stratégie JOINED
public class Encadrant extends Utilisateur {


    @Column(name = "specialite", nullable = false)
    private String specialite;

    @ManyToOne
    @JoinColumn(name = "filiere_id", nullable = false)
    private Filiere filiere;



}
