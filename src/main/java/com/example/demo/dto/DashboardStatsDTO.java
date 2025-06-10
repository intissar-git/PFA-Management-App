package com.example.demo.dto;

import lombok.Data;

@Data
public class DashboardStatsDTO {
    private int totalGroupes;
    private long totalProjets;
    private long projetsEnCours;
    private long projetsTermines;
    private double progresMoyen;
}