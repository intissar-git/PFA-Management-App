package com.example.demo.service;

import com.example.demo.model.Soutenance;
import com.example.demo.repository.SoutenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoutenanceService {

    @Autowired
    private SoutenanceRepository soutenanceRepository;

    // Méthode pour sauvegarder la soutenance dans la base de données
    public Soutenance saveSoutenance(Soutenance soutenance) {
        return soutenanceRepository.save(soutenance); // Sauvegarde dans la base de données
    }
}