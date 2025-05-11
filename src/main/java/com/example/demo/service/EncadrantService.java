package com.example.demo.service;

import com.example.demo.model.Encadrant;
import com.example.demo.model.Etudiant;
import com.example.demo.model.Utilisateur;
import com.example.demo.repository.EncadrantRepository;
import com.example.demo.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EncadrantService {

    @Autowired
    private EncadrantRepository encadrantRepository;
    private UtilisateurRepository utilisateurRepository;
    //recuperer tout les encadrant


    //ajouter un encadrant
    public Encadrant saveEncadrant(Encadrant encadrant) {
        return encadrantRepository.save(encadrant);
    }

    //recuperer tout les encadrant qui appartient a un departement
    public List<Encadrant> getAllEncadrantsByFiliereId(Long filiereId){
        return encadrantRepository.getAllEncadrantsByFiliereId(filiereId);
    }



}



