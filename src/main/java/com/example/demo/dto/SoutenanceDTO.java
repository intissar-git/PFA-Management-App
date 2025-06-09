package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class SoutenanceDTO {
    private Long groupeId;
    private String date;
    private String heure;
    private Long salleId;
    private List<Long> juryIds;
}