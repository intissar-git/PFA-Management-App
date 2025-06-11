package com.example.demo.service;

import com.example.demo.model.Etudiant;
import com.example.demo.model.Groupe;
import com.example.demo.repository.GroupeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public class GroupeService {

    @Autowired
    public GroupeRepository groupeRepository;

    //recupere tout les groupes
    public List<Groupe> getAllGroupe(){
        return groupeRepository.getAllGroupes();
    }

    //ajouter un groupe
    public Groupe AddGroupe(Groupe groupe){
        return groupeRepository.saveGroupe(groupe);
    }

    //recuperer les groupes d'une filiere
    public List<Groupe> getGroupesByFiliereId(int filiere_Id){
        return groupeRepository.findByFiliereId(filiere_Id);
    }

    //recuperé le titre de projet d'un groupe
    public String getProjectTitleByGroupeId(int idGroupe) {
        return groupeRepository.findProjectTitleByGroupeId(idGroupe);
    }
    //recuperé le titre de projet d'un groupe
    public String getEncadrantNameByGroupeId(int idGroupe) {
        return groupeRepository.findEncadrantByGroupeId(idGroupe);
    }

}
