package com.example.demo.repository;

import com.example.demo.model.Departement;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {

    // Récupérer tous les Departements
    default List<Departement> getAllDepartement() {
        return findAll();
    }
    // Sauvegarder un Departement
    default Departement saveDepartement(Departement departement) {
        return save(departement);
    }
}
