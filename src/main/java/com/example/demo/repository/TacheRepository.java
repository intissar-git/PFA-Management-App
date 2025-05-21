package com.example.demo.repository;

import com.example.demo.model.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Integer> {
    // Récupérer tout les taches

    default List<Tache> getAllTaches() {
        return findAll();
    }
    // Trouver les tâches par projet (id du projet)
    @Query("SELECT t FROM Tache t WHERE t.projet.id = :projetId")
    List<Tache> findByProjetId(@Param("projetId") int projetId);

    long countByProjetId(int projectId);

    long countByProjetIdAndStatut(int projectId, String statut);

    @Query("SELECT COUNT(t) FROM Tache t WHERE t.projet.id = :projetId AND t.dateLimite < CURRENT_DATE AND t.statut != 'terminé'")
    long countRetard(@Param("projetId") int projetId);

}
