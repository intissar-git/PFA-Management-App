package com.example.demo.model;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Filiere {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "intitule", nullable = false)
    private String intitule;

    @ManyToOne
    @JoinColumn(name = "departement_id", nullable = false)
    private Departement departement;






}
