package com.example.demo.controller;

import com.example.demo.dto.DashboardStatsDTO;
import com.example.demo.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}