package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.repository.LivrablesRepository;
import com.example.demo.repository.TacheRepository;
import com.example.demo.repository.UtilisateurRepository;
import com.example.demo.service.EtudiantService;
import com.example.demo.service.FichierService;
import com.example.demo.service.LivrablesServices;
import com.example.demo.service.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // Autoriser React à faire des requêtes

public class EspaceEtudiantController {
    @Autowired
    public TacheService tacheService;
    public TacheRepository tacheRepository;
    private final LivrablesServices livrablesServices;
    private final LivrablesRepository livrablesRepository;
    private final FichierService fichierService;
    public UtilisateurRepository utilisateurRepository;
    private final EtudiantService etudiantService;
    @Autowired
    public EspaceEtudiantController(
            LivrablesServices livrablesServices ,
            EtudiantService etudiantService,
            LivrablesRepository livrablesRepository,
            FichierService fichierService
    ){
        this.livrablesServices = livrablesServices;
        this.etudiantService=etudiantService;
        this.livrablesRepository=livrablesRepository;
        this.fichierService=fichierService;
    }

    //Ajouter une tache
    @PostMapping("/AddTache")
    public ResponseEntity<Tache> addSoutenance(@RequestBody Tache tache) {

        // Sauvegarder la tache dans la base de données
        Tache saveTache = tacheService.AddNewTache(tache);
        // Retourner la soutenance sauvegardée avec un code HTTP 201 (Créé)
        return ResponseEntity.status(201).body(saveTache);
    }
    //Aficher tout les taches
    @GetMapping("/Taches")
    public ResponseEntity<List<Tache>> getAllTaches() {
        List<Tache> tache = tacheService.getAllTaches();
        if(tache.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tache);
    }
    //supprimer une tache par id
    @DeleteMapping("/DeletTache/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        boolean isDeleted= tacheService.deleteTacheById(id);

        if (isDeleted) {

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //Ajouter une livrable
    /*@PostMapping("/AddLivrable")
    public ResponseEntity<Livrable> addLivrable(@RequestBody Livrable livrable) {

        // Sauvegarder la tache dans la base de données
        Livrable LivrableSeved = livrablesServices.AjouterUneLivrable(livrable);
        // Retourner la soutenance sauvegardée avec un code HTTP 201 (Créé)
        return ResponseEntity.status(201).body(LivrableSeved);
    }*/
    @PostMapping("/AddLivrable")
    public ResponseEntity<Livrable> ajouterLivrable(
            @RequestParam("nom_fichier") String nomFichier,
            @RequestParam(value = "descreption", required = false) String description,
            @RequestParam("tache_id") Integer tacheId,
            @RequestParam("fichier") MultipartFile fichier) {

        // 1. Créer et sauvegarder le livrable
        Tache tache = new Tache();
        tache.setId(tacheId);
        Livrable livrable = Livrable.builder()
                .nom_fichier(nomFichier)
                .descreption(description)
                .tache(tache)
                .build();

        Livrable livrableSauvegarde = livrablesRepository.save(livrable);

        // 2. Enregistrer le fichier lié
        fichierService.enregistrerFichier(fichier, livrableSauvegarde);

        // 3. Recharger le livrable avec son fichier associé
        Livrable livrableAvecFichier = livrablesRepository.findById(livrableSauvegarde.getId()).orElse(null);

        return ResponseEntity.ok(livrableAvecFichier);
    }


    //Afficher les livrables d'une tache specefique
    @GetMapping("/Livrables/{tacheId}")
    public ResponseEntity<List<Livrable>> getAllLivrablesByIdTache(@PathVariable int tacheId){
        List<Livrable> livrables = livrablesServices.getLivrableByid_Tache(tacheId);
        if(livrables.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(livrables);
    }
    @GetMapping("/Livrable")
    public ResponseEntity<List<Livrable>> getAllLivrablesE() {
        List<Livrable> liv = livrablesServices.getAllLivrables();
        if(liv.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(liv);
    }

    //récuperer id du projet d'un etudiant
    @GetMapping("/etudiant/{id}/projet")
    public ResponseEntity<?> getProjetIdByEtudiant(@PathVariable Long id) {
        Etudiant etudiant = etudiantService.findById(id).orElse(null);

        if (etudiant == null) {
            return ResponseEntity.notFound().build();
        }

        Groupe groupe = etudiant.getGroupe();
        if (groupe == null || groupe.getProjet() == null) {
            return ResponseEntity.badRequest().body("Groupe ou projet manquant");
        }

        Projet projet = groupe.getProjet();
        return ResponseEntity.ok(Collections.singletonMap("projet_id", projet.getId()));
    }
    //récuperer les taches d'un user
    @GetMapping("/taches/{etudiantId}")
    public ResponseEntity<List<Tache>> getTachesByEtudiant(@PathVariable Long etudiantId) {
        List<Tache> taches = tacheService.getTachesByEtudiantId(etudiantId);
        if (taches.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(taches);
    }
    //récupere les statistiques des taches d'un project

    @GetMapping("/stats/{idProject}")
    public ResponseEntity<?> getStats(@PathVariable int idProject) {
        return ResponseEntity.ok(tacheService.getStatsUtilisateur(idProject));
    }


}
