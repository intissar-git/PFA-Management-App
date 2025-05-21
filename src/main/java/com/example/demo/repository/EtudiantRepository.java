
package com.example.demo.repository;

import com.example.demo.model.Encadrant;
import com.example.demo.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    //recuperé les etudiants d'une filiere
    List<Etudiant> getAllEtudiantsByFiliereId(int Filier_Id);


}
