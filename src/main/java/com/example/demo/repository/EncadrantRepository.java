package com.example.demo.repository;

import com.example.demo.model.Encadrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface EncadrantRepository extends JpaRepository<Encadrant, Long> {

    // Récupérer tous les encadrants
    default List<Encadrant> getAllEncadrants() {
        return findAll();
    }
    //filtrer par userType

    //chercher par email

    // Sauvegarder un encadrant
    default Encadrant saveEncadrant(Encadrant encadrant) {

        return save(encadrant);
    }
    //recuperer les encadrant d'un departement
    List<Encadrant> getAllEncadrantsByFiliereId(Long filiereId);}
