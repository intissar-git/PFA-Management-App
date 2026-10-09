package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Soutenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "groupe_id", nullable = false)
    private Groupe groupe;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String heure;

    @Column(nullable = false)
    private String salle;

    @ManyToMany
    @JoinTable(
            name = "soutenance_jury",
            joinColumns = @JoinColumn(name = "soutenance_id"),
            inverseJoinColumns = @JoinColumn(name = "jury_id")
    )
    private List<Jury> jurys;
}