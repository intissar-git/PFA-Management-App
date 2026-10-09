package com.example.demo.service;

import com.example.demo.model.Tache;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TacheService {
    @Autowired
    public TacheRepository tacheRepository;
    private final EtudiantRepository etudiantRepository;
    @Autowired
    public TacheService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }
    public List<Tache> getAllTaches(){
        return tacheRepository.getAllTaches();
    }
    //save
    public void save(Tache tache) {
        tacheRepository.save(tache);
    }

    //ajouter une tache
    public Tache AddNewTache(Tache tache){
        return tacheRepository.save(tache);
    }
    //get tache by id
    public Tache getTacheById(int id_tache) {
        return tacheRepository.findById(id_tache)
                .orElse(null); // renvoie null si non trouvé
    }

    //supprimer par id
        public boolean deleteTacheById(int id) {
            if (tacheRepository.existsById(id)) {
                tacheRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        }
    public List<Tache> getTachesByEtudiantId(Long etudiantId) {
        return etudiantRepository.findById(etudiantId)
                .map(etudiant -> {
                    if (etudiant.getGroupe() == null || etudiant.getGroupe().getProjet() == null) {
                        return Collections.<Tache>emptyList(); // Liste vide typée
                    }

                    int projetId = etudiant.getGroupe().getProjet().getId(); // <- ici c'est bien un int
                    return tacheRepository.findByProjetId(projetId); // OK si projetId est un int
                })
                .orElse(Collections.emptyList());
    }
//statistiques des taches
public Map<String, Object> getStatsUtilisateur(int projectId) {
    long total = tacheRepository.countByProjetId(projectId);
    long termine = tacheRepository.countByProjetIdAndStatut(projectId, "terminé");
    long enCours = tacheRepository.countByProjetIdAndStatut(projectId, "en cours");
    long aFaire = tacheRepository.countByProjetIdAndStatut(projectId, "à faire");
    long enRetard = tacheRepository.countRetard(projectId);

    double pourcentageTermine = total == 0 ? 0 : ((double) termine / total) * 100;

    Map<String, Object> stats = new HashMap<>();
    stats.put("total", total);
    stats.put("termine", termine);
    stats.put("enCours", enCours);
    stats.put("aFaire", aFaire);
    stats.put("enRetard", enRetard);
    stats.put("pourcentageTermine", pourcentageTermine);

    return stats;
}
}
