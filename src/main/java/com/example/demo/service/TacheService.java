package com.example.demo.service;

import com.example.demo.model.Tache;
import com.example.demo.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TacheService {
    @Autowired
    public TacheRepository tacheRepository;
    public List<Tache> getAllTaches(){
        return tacheRepository.getAllTaches();
    }

    //ajouter une tache
    public Tache AddNewTache(Tache tache){
        return tacheRepository.save(tache);
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

}
