package com.example.demo.service;

import com.example.demo.model.DepotRapport;
import com.example.demo.repository.DepotRapportRepository;
import com.example.demo.repository.FiliereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepotRapportService {

    @Autowired
    private DepotRapportRepository depotRapportRepository;

    @Autowired
    private FiliereRepository filiereRepository;

    public DepotRapport ajouterDepotRapport(DepotRapport depotRapport) {
        Long filiereId = (long) depotRapport.getFiliere().getId();
        var filiere = filiereRepository.findById(filiereId).orElse(null);

        if (filiere == null) {
            throw new IllegalArgumentException("Filière introuvable avec l'id : " + filiereId);
        }

        depotRapport.setFiliere(filiere);
        return depotRapportRepository.save(depotRapport);
    }

    public List<DepotRapport> getAllDepots() {
        return depotRapportRepository.findAll();
    }
}
