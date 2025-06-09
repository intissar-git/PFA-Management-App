package com.example.demo.repository;

import com.example.demo.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Projet, Long> {
    @Query("SELECT COUNT(p) FROM Projet p")
    long countAllProjets();

    // Ajoutez d'autres méthodes de comptage si vous avez un statut pour les projets
}