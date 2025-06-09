package com.example.demo.service;

import com.example.demo.dto.AdminStatsDTO;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private EncadrantRepository encadrantRepository;

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private FiliereRepository filiereRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public AdminStatsDTO getAdminStatistics() {
        AdminStatsDTO stats = new AdminStatsDTO();

        stats.setTotalEtudiants(etudiantRepository.count());
        stats.setTotalEncadrants(encadrantRepository.count());
        stats.setTotalDepartements(departementRepository.count());
        stats.setTotalFilieres(filiereRepository.count());
        stats.setProjetsEnCours(projectRepository.countAllProjets()); // À adapter si vous avez un statut
        stats.setProjetsTermines(0); // À implémenter selon votre logique

        return stats;
    }
}