package com.example.demo.repository;

import com.example.demo.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Projet, Long> {
    @Query("SELECT COUNT(p) FROM Projet p")
    long countAllProjets();

    // Ajoutez d'autres méthodes de comptage si vous avez un statut pour les projets

    // Compter les projets d'un encadrant
    @Query("SELECT COUNT(p) FROM Projet p WHERE EXISTS " +
            "(SELECT 1 FROM Groupe g WHERE g.projet = p AND g.encadrant.id = :encadrantId)")
    long countByEncadrantId(@Param("encadrantId") Long encadrantId);

    // Compter les projets en cours d'un encadrant
    @Query("SELECT COUNT(p) FROM Projet p WHERE EXISTS " +
            "(SELECT 1 FROM Groupe g WHERE g.projet = p AND g.encadrant.id = :encadrantId AND g.statut = 'EN_COURS')")
    long countProjetsEnCoursByEncadrantId(@Param("encadrantId") Long encadrantId);

    // Compter les projets terminés d'un encadrant
    @Query("SELECT COUNT(p) FROM Projet p WHERE EXISTS " +
            "(SELECT 1 FROM Groupe g WHERE g.projet = p AND g.encadrant.id = :encadrantId AND g.statut = 'TERMINE')")
    long countProjetsTerminesByEncadrantId(@Param("encadrantId") Long encadrantId);
}