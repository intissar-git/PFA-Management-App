package com.example.demo.repository;

import com.example.demo.model.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface FiliereRepository extends JpaRepository<Filiere, Long> {

    // Récupérer tous les Filierre
    default List<Filiere> getAllFiliere() {
        return findAll();
    }

    // Sauvegarder un filiere
    default Filiere saveFiliere(Filiere filiere) {
        return save(filiere);
    }
    //recuperer les filiere d'un departement

    List<Filiere> findByDepartementId(int departementId);


}
