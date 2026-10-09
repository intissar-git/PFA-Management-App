package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MessageLivrable {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Livrable livrable;

    @ManyToOne
    private Utilisateur auteur;

    private String contenu;

    public Integer getId() {
        return id;
    }

    public Livrable getLivrable() {
        return livrable;
    }

    public Utilisateur getAuteur() {
        return auteur;
    }

    public String getContenu() {
        return contenu;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    private LocalDateTime dateEnvoi = LocalDateTime.now();

}
