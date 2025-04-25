package com.example.demo.model;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Livrable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String Nom_Fichier;

    @Column(nullable = false)
    private String Descreption;


    @Column(nullable = false, updatable = false)
    private LocalDate dateSoumission;

    @PrePersist
    protected void onCreate() {
        this.dateSoumission = LocalDate.now();
    }


    @ManyToOne
    @JoinColumn(name = "tache_id", nullable = false)
    private Tache tache;

}
