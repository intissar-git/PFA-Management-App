package com.example.demo.controller;

import com.example.demo.model.Filiere;
import com.example.demo.model.Groupe;
import com.example.demo.service.FiliereService;
import com.example.demo.service.GroupeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groupe")
public class GroupeController {
    @Autowired
    public GroupeService groupeService;

    //recuperer tout les groupes
   /* @GetMapping
    public List<Groupe> getAllGroupes(){
        return groupeService.getAllGroupe();
    }*/

    //ajouter un groupe
    @PostMapping
    public  Groupe AddGroupe(Groupe groupe){
        return groupeService.AddGroupe(groupe);
    }

}
