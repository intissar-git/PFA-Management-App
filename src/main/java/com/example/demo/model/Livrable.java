package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;

import lombok.Builder;
@Builder
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
    private String nom_fichier;

    @Column(nullable = false)
    private String descreption;

    @ManyToOne
    @JoinColumn(name = "tache_id", nullable = false)
    private Tache tache;

    @OneToOne(mappedBy = "livrable", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Fichier fichier;


}
