package com.example.demo.dto;

import lombok.Data;

@Data
public class AdminStatsDTO {
    private long totalEtudiants;
    private long totalEncadrants;
    private long totalDepartements;
    private long totalFilieres;
    private long projetsEnCours;
    private long projetsTermines;

    // Vous pouvez ajouter d'autres statistiques si nécessaire
}