package com.example.demo.controller;

import com.example.demo.model.Livrable;
import com.example.demo.model.Tache;
import com.example.demo.repository.TacheRepository;
import com.example.demo.service.LivrablesServices;
import com.example.demo.service.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // Autoriser React à faire des requêtes

public class EspaceEtudiantController {
    @Autowired
    public TacheService tacheService;
    public TacheRepository tacheRepository;
    public LivrablesServices livrablesServices;


    @Autowired
    public EspaceEtudiantController(LivrablesServices livrablesServices) {
        this.livrablesServices = livrablesServices;
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
    @PostMapping("/AddLivrable")
    public ResponseEntity<Livrable> addLivrable(@RequestBody Livrable livrable) {

        // Sauvegarder la tache dans la base de données
        Livrable LivrableSeved = livrablesServices.AjouterUneLivrable(livrable);
        // Retourner la soutenance sauvegardée avec un code HTTP 201 (Créé)
        return ResponseEntity.status(201).body(LivrableSeved);
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

}
