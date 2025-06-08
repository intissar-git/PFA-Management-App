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

    @Column(name = "text_notif", nullable = false, columnDefinition = "TEXT")
    private String textNotif;

    @Column(name = "date_creation", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @Column(name = "statut", nullable = false)
    private boolean statut; // true=lue, false=non lue

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    // Vous pouvez ajouter des pré-initialisations avec @PrePersist
    @PrePersist
    protected void onCreate() {
        this.dateCreation = new Date();
        this.statut = false; // Par défaut, la notification est non lue
    }
}