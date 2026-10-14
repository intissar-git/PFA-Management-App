package com.example.demo.controller;

import com.example.demo.model.Departement;
import com.example.demo.service.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/departement")

public class DepartementController
{
    @Autowired
    public DepartementService departementService;

    //recuperer tout les departements
    @GetMapping
    public List<Departement> getAllDepartement(){
        return departementService.getAllDepartement();
    }
    //ajouter un departement
    @PostMapping
    public  Departement addDepartement(Departement departement){
        return departementService.addDepartement(departement);
    }
}
