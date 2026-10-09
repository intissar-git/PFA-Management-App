package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String textNotif;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    private boolean statut; // false = non lue, true = lue

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @PrePersist
    protected void onCreate() {
        this.dateCreation = new Date();
        this.statut = false;
    }
}