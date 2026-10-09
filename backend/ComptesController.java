package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    private EtudiantRepository etudiantRepository;

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
    public ResponseEntity<List<Encadrant>> getEncadrantsByDepartementId(@PathVariable int id) {
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
    //get info user by id
    @GetMapping("/UserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<Utilisateur> utilisateur = utilisateurService.findById(id);
        if (utilisateur.isPresent()) {
            return ResponseEntity.ok(utilisateur.get());
        } else {
            return ResponseEntity.status(404).body("utilisateur non trouvé");
        }
    }
    @GetMapping("/utilisateur/{adresseEmail}")  // URL plus explicite
    public ResponseEntity<?> getUserByMail(@PathVariable String adresseEmail) {
        Optional<Utilisateur> utilisateur = utilisateurService.findByAdresseEmail(adresseEmail);
        return utilisateur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
