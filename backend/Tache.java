package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Date;

@Getter
@Setter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tache {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String statut;


    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd") // important pour Spring + Jackson
    private Date dateLimite;

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;
}