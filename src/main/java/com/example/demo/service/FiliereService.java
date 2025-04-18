package com.example.demo.service;

import com.example.demo.model.Filiere;
import com.example.demo.repository.FiliereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FiliereService {
    @Autowired
    public FiliereRepository filiereRepository;
    //recupere tout les filieres
    public List<Filiere> getAllFiliere(){
        return filiereRepository.getAllFiliere();
    }
    //ajouter une filiere
    public Filiere addFiliere(Filiere filiere){
        return filiereRepository.saveFiliere(filiere);
    }
    //recuperer les filieres d'un departement
    public List<Filiere> getFiliereByDepartementId(int departement_id){
        return filiereRepository.findByDepartementId(departement_id);
    }
}
