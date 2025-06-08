package com.example.demo.controller;

import com.example.demo.model.Soutenance;
import com.example.demo.service.SoutenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/soutenances")
public class SoutenaceController {

    @Autowired
    private SoutenanceService soutenanceService;

    @PostMapping
    public Soutenance createSoutenance(@RequestBody Soutenance soutenance) {
        return soutenanceService.saveSoutenance(soutenance);
    }

    @GetMapping
    public List<Soutenance> getAllSoutenances() {
        return soutenanceService.getAllSoutenances();
    }

    @DeleteMapping("/{id}")
    public void deleteSoutenance(@PathVariable Long id) {
        soutenanceService.deleteSoutenance(id);
    }

    @GetMapping("/groupe/{groupeId}")
    public List<Soutenance> getSoutenancesByGroupe(@PathVariable Long groupeId) {
        return soutenanceService.getSoutenancesByGroupe(groupeId);
    }
}