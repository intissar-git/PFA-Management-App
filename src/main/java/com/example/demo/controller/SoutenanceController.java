package com.example.demo.controller;

import com.example.demo.model.Soutenance;
import com.example.demo.service.SoutenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/soutenance")
public class SoutenanceController {

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
}