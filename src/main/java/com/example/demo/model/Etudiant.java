package com.example.demo.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)  // Stratégie JOINED
public class Etudiant extends Utilisateur {



    @ManyToOne
    @JoinColumn(name = "groupe_id", nullable =true)
    private Groupe groupe;

    @ManyToOne
    @JoinColumn(name = "filiere_id", nullable = false)
    private Filiere filiere;
    @Column(name = "Code_APOGEE",nullable = false)
    private String  code_APOGEE;

}
