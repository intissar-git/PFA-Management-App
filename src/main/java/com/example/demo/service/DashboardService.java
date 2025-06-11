package com.example.demo.service;

import com.example.demo.dto.DashboardStatsDTO;
import com.example.demo.dto.GroupeEtudiantsDTO;
import com.example.demo.dto.GroupeStatsDTO;
import com.example.demo.model.Groupe;
import com.example.demo.model.Projet;
import com.example.demo.model.Tache;
import com.example.demo.repository.GroupeRepository;
import com.example.demo.repository.TacheRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Groupe> groupes = groupeRepository.findByEncadrantId(encadrantId);

        stats.setTotalGroupes(groupes.size());

        List<Groupe> groupesWithProjet = groupes.stream()
                .filter(g -> g.getProjet() != null)
                .collect(Collectors.toList());

        stats.setTotalProjets(groupesWithProjet.size());

        int projetsEnCours = 0;
        int projetsTermines = 0;
        int projetsEnRetard = 0;
        double totalProgres = 0;

        for (Groupe groupe : groupesWithProjet) {
            List<Tache> taches = tacheRepository.findByProjetId(groupe.getProjet().getId());
            long totalTaches = taches.size();
            long tachesTerminees = taches.stream()
                    .filter(t -> "terminé".equalsIgnoreCase(t.getStatut()))
                    .count();

            double progress = totalTaches > 0 ? (tachesTerminees * 100.0 / totalTaches) : 0;
            totalProgres += progress;

            boolean hasRetard = taches.stream()
                    .anyMatch(t -> t.getDateLimite() != null
                            && t.getDateLimite().before(new Date())
                            && !"terminé".equalsIgnoreCase(t.getStatut()));

            if (totalTaches == 0) {
                projetsEnCours++;
            } else if (tachesTerminees == totalTaches) {
                projetsTermines++;
            } else if (hasRetard) {
                projetsEnRetard++;
            } else {
                projetsEnCours++;
            }
        }

        stats.setProjetsEnCours(projetsEnCours);
        stats.setProjetsTermines(projetsTermines);
        stats.setProjetsEnRetard(projetsEnRetard);
        stats.setProgresMoyen(groupesWithProjet.isEmpty() ? 0 : totalProgres / groupesWithProjet.size());

        return stats;
    }

    public List<GroupeStatsDTO> getGroupesStats(Long encadrantId) {
        List<Groupe> groupes = groupeRepository.findByEncadrantId(encadrantId);
        List<GroupeStatsDTO> result = new ArrayList<>();

        for (Groupe groupe : groupes) {
            GroupeStatsDTO stats = new GroupeStatsDTO();
            stats.setId(Long.valueOf(groupe.getId()));
            stats.setIntitule(groupe.getIntitule());

            if (groupe.getProjet() == null) {
                stats.setProjetTitre("Aucun projet");
                stats.setProgress(0);
                stats.setStatus("en_cours");
                result.add(stats);
                continue;
            }

            stats.setProjetTitre(groupe.getProjet().getTitre());
            List<Tache> taches = tacheRepository.findByProjetId(groupe.getProjet().getId());
            long totalTaches = taches.size();
            long tachesTerminees = taches.stream()
                    .filter(t -> "terminé".equalsIgnoreCase(t.getStatut()))
                    .count();

            double progress = totalTaches > 0 ? (tachesTerminees * 100.0 / totalTaches) : 0;
            stats.setProgress(progress);

            // Nouvelle logique de statut
            if (totalTaches == 0) {
                stats.setStatus("en_cours");
            } else if (progress >= 100) {
                stats.setStatus("termine");
            } else {
                boolean hasRetard = taches.stream()
                        .anyMatch(t -> t.getDateLimite() != null
                                && t.getDateLimite().before(new Date())
                                && !"terminé".equalsIgnoreCase(t.getStatut()));

                stats.setStatus(hasRetard ? "en_retard" : "en_cours");
            }

            result.add(stats);
        }

        return result;
    }


    public List<GroupeEtudiantsDTO> countEtudiantsParGroupe(Long encadrantId) {
        List<Groupe> groupes = groupeRepository.findByEncadrantId(encadrantId);

        return groupes.stream().map(groupe -> {
            GroupeEtudiantsDTO dto = new GroupeEtudiantsDTO();
            dto.setGroupeId(Long.valueOf(groupe.getId()));
            dto.setGroupeIntitule(groupe.getIntitule());
            dto.setProjetTitre(groupe.getProjet() != null ? groupe.getProjet().getTitre() : "Aucun projet");

            // Comptez les étudiants dans ce groupe
            int count = groupe.getEtudiants() != null ? groupe.getEtudiants().size() : 0;
            dto.setNombreEtudiants(count);

            return dto;
        }).collect(Collectors.toList());
    }
}