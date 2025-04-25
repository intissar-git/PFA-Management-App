package com.example.demo.repository;

import com.example.demo.model.Groupe;
import com.example.demo.model.Livrable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LivrablesRepository extends JpaRepository<Livrable, Integer> {
    // Récupérer tout les taches
    default List<Livrable> getAllLivrable() {
        return findAll();
    }
    List<Livrable> findByTacheId(int TacheId);

}