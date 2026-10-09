package com.example.demo.dto;

import lombok.Data;

@Data
public class GroupeEtudiantsDTO {
    private Long groupeId;
    private String groupeIntitule;
    private String projetTitre;
    private int nombreEtudiants;
}