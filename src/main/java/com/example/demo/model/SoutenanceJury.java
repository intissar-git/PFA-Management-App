package com.example.demo.model;
import lombok.*;
import jakarta.persistence.*;

public class SoutenanceJury {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "soutenance_id", nullable = false)
    private Soutenance soutenance;

    @ManyToOne
    @JoinColumn(name = "jury_id", nullable = false)
    private Jury jury;
}
