package com.example.demo.service;

import com.example.demo.model.Encadrant;
import com.example.demo.model.Etudiant;
import com.example.demo.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    //l'ajoute d'un etudiant
    public Etudiant saveEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }
    //filtré les etudiants d'une filiere
    public List<Etudiant> getAllEtudiantsByFiliere(int filiere_id){
        return etudiantRepository.getAllEtudiantsByFiliereId(filiere_id);
    }
    //
    public Optional<Etudiant> findById(Long id) {
        return etudiantRepository.findById(id);
    }

}



