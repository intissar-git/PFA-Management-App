package com.example.demo.controller;

import com.example.demo.dto.DashboardStatsDTO;
import com.example.demo.dto.GroupeEtudiantsDTO;
import com.example.demo.dto.GroupeStatsDTO;
import com.example.demo.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/encadrant/{encadrantId}")
    public DashboardStatsDTO getEncadrantDashboardStats(@PathVariable Long encadrantId) {
        return dashboardService.getDashboardStats(encadrantId);
    }
    @GetMapping("/encadrant/{encadrantId}/groupes")
    public List<GroupeStatsDTO> getGroupesStats(@PathVariable Long encadrantId) {
        return dashboardService.getGroupesStats(encadrantId);
    }


    @GetMapping("/encadrant/{encadrantId}/etudiants-par-groupe")
    public ResponseEntity<List<GroupeEtudiantsDTO>> getEtudiantsParGroupe(@PathVariable Long encadrantId) {
        List<GroupeEtudiantsDTO> result = dashboardService.countEtudiantsParGroupe(encadrantId);
        return ResponseEntity.ok(result);
    }



}