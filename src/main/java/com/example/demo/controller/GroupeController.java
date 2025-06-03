package com.example.demo.controller;

import com.example.demo.model.Encadrant;
import com.example.demo.model.Filiere;
import com.example.demo.repository.EncadrantRepository;
import com.example.demo.repository.FiliereRepository;
import com.example.demo.repository.ProjectRepository;

import org.springframework.transaction.annotation.Transactional;
import com.example.demo.model.Groupe;
import com.example.demo.model.Projet;
import com.example.demo.service.EtudiantService;
import com.example.demo.model.CreateGroupWithProjectRequest;
import com.example.demo.service.FiliereService;
import com.example.demo.service.GroupeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groupe")
public class GroupeController {
    @Autowired
    public GroupeService groupeService;
    @Autowired
    public EtudiantService etudiantService;
    @Autowired
    private EncadrantRepository encadrantRepository;
    @Autowired
    private FiliereRepository filiereRepository;
    @Autowired
    private ProjectRepository projetRepository;

    //recuperer tout les groupes
   @GetMapping
    public List<Groupe> getAllGroupes(){
        return groupeService.getAllGroupe();
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
    // Dans GroupeController.java
    @Transactional
    @PostMapping("/createWithProject")
    public ResponseEntity<?> createGroupWithProject(
            @RequestBody CreateGroupWithProjectRequest request) {

        try {
            // 1. Valider et charger les entités existantes
            Encadrant encadrant = encadrantRepository.findById((long) request.getEncadrantId())
                    .orElseThrow(() -> new RuntimeException("Encadrant non trouvé"));
            Filiere filiere = filiereRepository.findById(request.getFiliereId())
                    .orElseThrow(() -> new RuntimeException("Filière non trouvée"));

            // 2. Créer et persister le projet
            Projet projet = new Projet();
            projet.setTitre(request.getProjectTitle());
            projet.setDescription(request.getProjectDescription());
            projet = projetRepository.save(projet); // Persistez explicitement

            // 3. Créer le groupe
            Groupe groupe = new Groupe();
            groupe.setIntitule(request.getGroupName());
            groupe.setDescription("Groupe pour le projet " + request.getProjectTitle());
            groupe.setProjet(projet);
            groupe.setEncadrant(encadrant);
            groupe.setFiliere(filiere);

            // 4. Sauvegarder le groupe
            Groupe savedGroup = groupeService.AddGroupe(groupe);

            // 5. Assigner les étudiants
            etudiantService.assignStudentsToGroup(request.getStudentIds(), savedGroup.getId());

            return ResponseEntity.ok(savedGroup);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "error", "Erreur lors de la création",
                            "details", e.getMessage()
                    ));
        }
    }
    //ajouter un groupe
    @PostMapping
    public  Groupe AddGroupe(Groupe groupe){
        return groupeService.AddGroupe(groupe);
    }

}
