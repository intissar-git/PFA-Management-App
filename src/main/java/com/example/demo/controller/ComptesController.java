package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // Autoriser React à faire des requêtes
public class ComptesController {

    @Autowired
    private DepartementService departementService;

    @Autowired
    private FiliereService filiereService;

    @Autowired
    private EncadrantService encadrantService;

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private GroupeService groupeService;

    @Autowired
    private ProjetService projetService;

    @Autowired
    private JuryService juryService;

    @Autowired
    private TacheService tacheService;

    @Autowired
    private UtilisateurService utilisateurService;

    // Liste des départements
    @GetMapping("/departements")
    public ResponseEntity<List<Departement>> getAllDepartement() {
        return ResponseEntity.ok(departementService.getAllDepartement());
    }

    // Liste des Filières
    @GetMapping("/filieres/{id}")
    public ResponseEntity<List<Filiere>> getFilieresByDepartementId(@PathVariable int id) {
        List<Filiere> filieres = filiereService.getFiliereByDepartementId(id);
        if (filieres.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content si aucune filière trouvée
        }
        return ResponseEntity.ok(filieres); // 200 OK avec les filières trouvées
    }

    // Liste des Encadrants
    @GetMapping("/Encadrants/{id}")
    public ResponseEntity<List<Encadrant>> getEncadrantsByDepartementId(@PathVariable Long id) {
        List<Encadrant> encadrants = encadrantService.getAllEncadrantsByFiliereId(id);
        if (encadrants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(encadrants);
    }

    // Liste des Etudiants
    @GetMapping("/Etudiants/{id}")
    public ResponseEntity<List<Etudiant>> getAllEtudiantsByFiliereId(@PathVariable int id) {
        List<Etudiant> etudiants = etudiantService.getAllEtudiantsByFiliere(id);
        if (etudiants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(etudiants);
    }

    // Liste des groupes par filière
    @GetMapping("/Groupes/{id}")
    public ResponseEntity<List<Groupe>> getAllGroupeByFiliereId(@PathVariable int id) {
        List<Groupe> groupes = groupeService.getGroupesByFiliereId(id);
        if (groupes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(groupes);
    }

    // L'intitulé d'un projet d'un groupe sélectionné
    @GetMapping("ProjetctName/{idGroupe}")
    public ResponseEntity<String> getProjectNameByIdGroupe(@PathVariable int idGroupe) {
        String NameProject = groupeService.getProjectTitleByGroupeId(idGroupe);

        if (NameProject == null || NameProject.trim().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(NameProject);
    }

    // Récupérer l'encadrant d'un groupe
    @GetMapping("EncadrantName/{idGroupe}")
    public ResponseEntity<String> getEncadrantNameByIdGroupe(@PathVariable int idGroupe) {
        String EncadrantName = groupeService.getEncadrantNameByGroupeId(idGroupe);

        if (EncadrantName == null || EncadrantName.trim().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(EncadrantName);
    }

    // Récupérer tous les jurys
    @GetMapping("Jurys")
    public ResponseEntity<List<Jury>> getAllJury() {
        List<Jury> jury = juryService.getAllJury();
        if (jury.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(jury);
    }

    // Insérer une nouvelle soutenance (à implémenter)

    // Enregistrer les codes de PFE générés
    @PutMapping("/utilisateur/update-codes")
    public ResponseEntity<Void> updateUtilisateursCodes(@RequestBody List<Utilisateur> utilisateurs) {
        utilisateurService.updateCodes(utilisateurs);
        return ResponseEntity.ok().build();
    }
}
