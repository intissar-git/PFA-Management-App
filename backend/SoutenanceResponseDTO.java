package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class SoutenanceResponseDTO {
    private Long id;
    private GroupeDTO groupe;
    private String date;
    private String heure;
    private SalleDTO salle;
    private List<JuryDTO> jurys;
}

// Classes DTO supplémentaires (GroupeDTO, SalleDTO, JuryDTO) à créer de manière similaire