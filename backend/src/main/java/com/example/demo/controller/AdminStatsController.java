package com.example.demo.controller;

import com.example.demo.dto.AdminStatsDTO;
import com.example.demo.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/stats")
public class AdminStatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping
    public AdminStatsDTO getAdminStats() {
        return statsService.getAdminStatistics();
    }
}