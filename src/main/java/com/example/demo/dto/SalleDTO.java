package com.example.demo.dto;

import lombok.Data;

@Data
public class SalleDTO {
    private Long id;
    private String nom;
    private int capacite;
    private BlocDTO bloc;
}