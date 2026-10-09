package com.example.demo.dto;

import lombok.Data;

@Data
public class GroupeDTO {
    private Long id;
    private String intitule;
    private String projet;
    private EncadrantDTO encadrant;
    private FiliereDTO filiere;
}