package com.example.demo.service;

import com.example.demo.model.Etudiant;
import com.example.demo.model.Utilisateur;
import com.example.demo.model.Encadrant;
import com.example.demo.repository.EncadrantRepository;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    @Autowired
    private EncadrantRepository encadrantRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    // Ajouter un nouvel Encadrant
    public Encadrant saveEncadrant(Encadrant encadrant) {
        return encadrantRepository.save(encadrant);
    }

    // Ajouter un nouvel Etudiant
    public Etudiant saveEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    // Récupérer un encadrant par son adresse email
    public Encadrant getEncadrantByEmail(String email) {
        return encadrantRepository.findByAdresseEmail(email);
    }

    // Récupérer un étudiant par son adresse email
    public Etudiant getEtudiantByApogee(int apogee) {
        return etudiantRepository.findByCodeApogee(apogee);
    }

    // Mettre à jour un encadrant
    public Encadrant mettreAJourEncadrant(Encadrant encadrant) {
        return encadrantRepository.save(encadrant);
    }

    // Mettre à jour un étudiant
    public Etudiant mettreAJourEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    // Supprimer un encadrant
    public void supprimerEncadrant(Long id) {
        encadrantRepository.deleteById(id);
    }

    // Supprimer un étudiant
    public void supprimerEtudiant(Long id) {
        etudiantRepository.deleteById(id);
    }
}
