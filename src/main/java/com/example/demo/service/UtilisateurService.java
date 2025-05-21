package com.example.demo.service;

import com.example.demo.model.Etudiant;
import com.example.demo.model.Utilisateur;
import com.example.demo.model.Encadrant;
import com.example.demo.repository.EncadrantRepository;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UtilisateurService implements UserDetailsService  {

    @Autowired
    private EncadrantRepository encadrantRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Ajouter un nouvel Encadrant
    public Encadrant saveEncadrant(Encadrant encadrant) {
        return encadrantRepository.save(encadrant);
    }

    // Ajouter un nouvel Etudiant
    public Etudiant saveEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utilisateurRepository.findByAdresseEmail(username).orElseThrow();
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

    //générer le code pour les encadrants et les etudiants


    @Transactional
    public void updateCodes(List<Utilisateur> utilisateur) {
        utilisateur.forEach(dto -> {
            utilisateurRepository.updateCodePfe(dto.getId(), dto.getPassword());
        });
    }
    /*public void updateCodes(List<Utilisateur> utilisateur) {
        for (Utilisateur dto :utilisateur) {
            Utilisateur utilisateur1 = utilisateurRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Not found"));
            utilisateur1.setCodePfe(dto.getCodePfe());
            utilisateurRepository.save(utilisateur1);
        }
    }*/

    public Optional<Utilisateur> findById(Long id) {
        return utilisateurRepository.findById(id);
    }


}
