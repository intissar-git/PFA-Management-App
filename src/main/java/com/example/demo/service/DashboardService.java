package com.example.demo.service;

import com.example.demo.dto.DashboardStatsDTO;
import com.example.demo.model.Groupe;
import com.example.demo.model.Projet;
import com.example.demo.repository.GroupeRepository;
import com.example.demo.repository.TacheRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final GroupeRepository groupeRepository;
    private final TacheRepository tacheRepository;

    public DashboardService(GroupeRepository groupeRepository,
                            TacheRepository tacheRepository) {
        this.groupeRepository = groupeRepository;
        this.tacheRepository = tacheRepository;
    }

    public DashboardStatsDTO getDashboardStats(Long encadrantId) {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        // Récupérer tous les groupes de l'encadrant
        List<Groupe> groupes = groupeRepository.findByEncadrantId(encadrantId);
        stats.setTotalGroupes(groupes.size());

        int projetsEnCours = 0;
        int projetsTermines = 0;
        double totalProgres = 0;
        int groupesAvecProjet = 0;

        for (Groupe groupe : groupes) {
            Projet projet = groupe.getProjet();
            if (projet != null) {
                groupesAvecProjet++;

                // Compter les tâches du projet
                long totalTaches = tacheRepository.countByProjetId(projet.getId());
                long tachesTerminees = tacheRepository.countByProjetIdAndStatut(projet.getId(), "terminé");

                // Calculer le progrès
                double progres = (totalTaches > 0) ? ((double) tachesTerminees / totalTaches) * 100 : 0;
                totalProgres += progres;

                // Déterminer le statut du projet
                if (totalTaches > 0 && totalTaches == tachesTerminees) {
                    projetsTermines++;
                } else {
                    projetsEnCours++;
                }
            }
        }

        stats.setTotalProjets(groupesAvecProjet);
        stats.setProjetsEnCours(projetsEnCours);
        stats.setProjetsTermines(projetsTermines);
        stats.setProgresMoyen(groupesAvecProjet > 0 ? totalProgres / groupesAvecProjet : 0);

        return stats;
    }
}