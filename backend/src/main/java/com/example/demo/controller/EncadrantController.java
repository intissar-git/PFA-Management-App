package com.example.demo.controller;

import com.example.demo.model.Encadrant;
import com.example.demo.service.EncadrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/encadrant")

public class EncadrantController {

    @Autowired
    private EncadrantService encadrantService;



    @PostMapping
    public Encadrant createEtudiant(@RequestBody Encadrant encadrant) {
        return encadrantService.saveEncadrant(encadrant);
    }
    //espace etudiant

}






