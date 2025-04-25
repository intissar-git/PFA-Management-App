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
    public DepartementService departementService;
    @Autowired
    public FiliereService filiereService;
    @Autowired
    public EncadrantService encadrantService;
    @Autowired
    public EtudiantService etudiantService;
    @Autowired
    public GroupeService groupeService;
    @Autowired
    public ProjetService projetService;
    @Autowired
    public JuryService juryService;
    @Autowired
    public  SoutenanceService soutenanceService;
    @Autowired
    public TacheService tacheService;
    @Autowired
    public UtilisateurService utilisateurService;

    // Liste des departements
    @GetMapping("/departements")
    public ResponseEntity<List<Departement>> getAllDepartement() {
        return ResponseEntity.ok(departementService.getAllDepartement());
    }
    // Liste des Filieres
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
        List<Encadrant> encadrants = encadrantService.getAllEncadrantsByDepartementId(id);
        if(encadrants.isEmpty()){
        return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(encadrants);

    }
    // Liste des Etudiants
    @GetMapping("/Etudiants/{id}")
    public ResponseEntity<List<Etudiant>> getAllEtudiantsByFiliereId(@PathVariable int id) {
        List<Etudiant> etudiants = etudiantService.getAllEtudiantsByFiliere(id);
        if(etudiants.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(etudiants);
    }
    //liste des groupe par filiere

    @GetMapping("/Groupes/{id}")

    public ResponseEntity<List<Groupe>> getAllGroupeByFiliereId(@PathVariable int id) {
        List<Groupe> groupes = groupeService.getGroupesByFiliereId(id);
        if(groupes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(groupes);
    }
    // l'intituler d'un projet d'un groupe selectioné

    @GetMapping("ProjetctName/{idGroupe}")

    public ResponseEntity<String> getProjectNameByIdGroupe(@PathVariable int idGroupe){
        String NameProject = groupeService.getProjectTitleByGroupeId(idGroupe);

        if (NameProject == null || NameProject.trim().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(NameProject);
    }
    //recuperer l'encadrant d'un groupe
    @GetMapping("EncadrantName/{idGroupe}")

    public ResponseEntity<String> getEncadrantNameByIdGroupe(@PathVariable int idGroupe){
        String EncadrantName = groupeService.getEncadrantNameByGroupeId(idGroupe);

        if (EncadrantName == null || EncadrantName.trim().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(EncadrantName);
    }
    //recupere tout les jury
    @GetMapping("Jurys")
    public ResponseEntity<List<Jury>> getAllJury() {
        List<Jury> jury = juryService.getAllJury();
        if(jury.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(jury);
    }
    //inseré une nouvelle soutenance
    @PostMapping("Soutenance")

    public ResponseEntity<Soutenance> addSoutenance(@RequestBody Soutenance soutenance) {
        // Sauvegarder la soutenance dans la base de données
        Soutenance savedSoutenance = soutenanceService.saveSoutenance(soutenance);
        // Retourner la soutenance sauvegardée avec un code HTTP 201 (Créé)
        return ResponseEntity.status(201).body(savedSoutenance);

    }

    //enregestré les codes de pfe générer

    @PutMapping("/utilisateur/update-codes")
    public ResponseEntity<Void> updateUtilisateursCodes(@RequestBody List<Utilisateur> utilisateurs) {
        utilisateurService.updateCodes(utilisateurs);
        return ResponseEntity.ok().build();
    }


}
