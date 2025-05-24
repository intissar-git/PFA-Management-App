package com.example.demo.service;

import com.example.demo.model.Livrable;
import com.example.demo.repository.LivrablesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivrablesServices {

    @Autowired
    public LivrablesRepository livrablesRepository;

    //recuperer tout les livrables
    public  List<Livrable> getAllLivrables(){
        return livrablesRepository.getAllLivrable();
    }

    //avec id Tache
    public List<Livrable> getLivrableByid_Tache(int id_Tache){
        return livrablesRepository.findByTacheId(id_Tache);
    }

    //Ajouter une livrable
    public Livrable AjouterUneLivrable(Livrable livrable){
        return livrablesRepository.save(livrable);
    }

    //reécuperer les info d'une livrable par son id
    public Optional<Livrable>  getLivrableById(int id_livrable){
        return livrablesRepository.findById(id_livrable);
    }
}
