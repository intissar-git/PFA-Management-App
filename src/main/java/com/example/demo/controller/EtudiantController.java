package com.example.demo.controller;

import com.example.demo.model.Etudiant;

import com.example.demo.model.Tache;
import com.example.demo.service.EtudiantService;
import com.example.demo.service.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;
    @Autowired
    private TacheService tacheService;



  //recuper tout les taches

}
