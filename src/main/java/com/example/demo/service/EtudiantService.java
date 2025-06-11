package com.example.demo.service;

import com.example.demo.model.Encadrant;
import com.example.demo.model.Etudiant;
import com.example.demo.model.Groupe;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.repository.GroupeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired  // Ajoutez cette injection
    private GroupeRepository groupeRepository;


    //l'ajoute d'un etudiant
    public Etudiant saveEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }
    //filtré les etudiants d'une filiere
    public List<Etudiant> getAllEtudiantsByFiliere(int filiere_id){
        return etudiantRepository.getAllEtudiantsByFiliereId(filiere_id);
    }
    //recuperé le titre de projet d'un groupe
    public List<Etudiant> getEtudiantByGroupeId(int idGroupe) {
        return etudiantRepository.findByGroupeId(idGroupe);
    }
    //

    public Optional<Etudiant> findById(Long id) {
        return etudiantRepository.findById(id);
    }
    // Dans EtudiantService.java

    @Transactional
    public void assignStudentsToGroup(List<Integer> studentIds, int groupId) {
        Groupe groupe = groupeRepository.findById((long) groupId)
                .orElseThrow(() -> new RuntimeException("Groupe non trouvé"));

        List<Etudiant> etudiants = etudiantRepository.findAllById(
                studentIds.stream().map(Long::valueOf).collect(Collectors.toList())
        );

        if (etudiants.size() != studentIds.size()) {
            throw new RuntimeException("Certains étudiants n'existent pas");
        }

        etudiants.forEach(etudiant -> {
            etudiant.setGroupe(groupe);
            etudiantRepository.save(etudiant);
        });
    }
}



