package com.example.demo.service;

import com.example.demo.model.Etudiant;
import com.example.demo.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;


    //la rechercher par code apogee
    public Etudiant getEtudiantByCodeApogee(int codeApogee) {
        return etudiantRepository.findByCodeApogee(codeApogee);
    }
    //l'ajoute d'un etudiant
    public Etudiant saveEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }
    //filtré les etudiants d'une filiere
    public List<Etudiant> getAllEtudiantsByFiliere(int filiere_id){
        return etudiantRepository.getAllEtudiantsByFiliereId(filiere_id);
    }

        }



