package com.example.demo.repository;

import com.example.demo.model.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Long> {
    // Récupérer tout les taches

    default List<Tache> getAllTaches() {
        return findAll();
    }

}
