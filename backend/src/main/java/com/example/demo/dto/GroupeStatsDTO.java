package com.example.demo.dto;

import lombok.Data;

@Data
public class GroupeStatsDTO {
    private Long id;
    private String intitule;
    private double progress;
    private String status; // "termine", "en_cours", "en_retard"
    private String projetTitre;
}