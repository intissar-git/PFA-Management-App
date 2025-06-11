package com.example.demo.dto;

import lombok.Data;

@Data
public class DashboardStatsDTO {
    private int totalGroupes;
    private int totalProjets;
    private int projetsEnCours;
    private int projetsTermines;
    private int projetsEnRetard;
    private double progresMoyen;
}