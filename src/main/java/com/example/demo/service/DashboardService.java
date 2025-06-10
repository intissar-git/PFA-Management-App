package com.example.demo.service;

import com.example.demo.dto.DashboardStatsDTO;
import com.example.demo.repository.GroupeRepository;
import com.example.demo.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final GroupeRepository groupeRepository;
    private final ProjectRepository projectRepository;

    public DashboardService(GroupeRepository groupeRepository,
                            ProjectRepository projectRepository) {
        this.groupeRepository = groupeRepository;
        this.projectRepository = projectRepository;
    }

    public DashboardStatsDTO getDashboardStats(Long encadrantId) {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        // Nombre total de groupes pour cet encadrant
        stats.setTotalGroupes(groupeRepository.countByEncadrantId(encadrantId));

        // Nombre total de projets pour cet encadrant
        stats.setTotalProjets(projectRepository.countByEncadrantId(encadrantId));

        // Nombre de projets en cours pour cet encadrant
        stats.setProjetsEnCours(projectRepository.countProjetsEnCoursByEncadrantId(encadrantId));

        // Nombre de projets terminés pour cet encadrant
        stats.setProjetsTermines(projectRepository.countProjetsTerminesByEncadrantId(encadrantId));

        // Calcul du progrès moyen des groupes de cet encadrant
        Double progresMoyen = groupeRepository.calculateProgresMoyenByEncadrantId(encadrantId);
        stats.setProgresMoyen(progresMoyen != null ? progresMoyen : 0.0);

        return stats;
    }
}