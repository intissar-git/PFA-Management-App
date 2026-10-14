package com.example.demo.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class SoutenanceRequestDTO {
    private Long groupeId;
    private Date date;
    private String heure;
    private String salle;
    private List<Long> juryIds;
}