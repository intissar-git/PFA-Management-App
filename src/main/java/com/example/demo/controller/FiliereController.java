package com.example.demo.controller;

import com.example.demo.model.Filiere;
import com.example.demo.service.FiliereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/filiere")
public class FiliereController {
    @Autowired
    public FiliereService filiereService;

    //recuperer tout les filieres
    @GetMapping
    public List<Filiere> getAllDepartement(){
        return filiereService.getAllFiliere();
    }
    //ajouter une filiere
    @PostMapping
    public  Filiere addDepartement(Filiere filiere){
        return filiereService.addFiliere(filiere);
    }

    @GetMapping("/{departementId}")
    public List<Filiere> getFilieresByDepartement(@PathVariable int departementId) {
        return filiereService.getFiliereByDepartementId(departementId);
    }
}
