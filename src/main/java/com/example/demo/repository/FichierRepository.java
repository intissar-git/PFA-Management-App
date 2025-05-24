package com.example.demo.repository;

import com.example.demo.model.Encadrant;
import com.example.demo.model.Etudiant;
import com.example.demo.model.Fichier;
import com.example.demo.model.Livrable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository

public interface FichierRepository extends JpaRepository<Fichier,Integer> {
    Fichier findByLivrable(Livrable livrable);
    Optional<Fichier> findByLivrableId(Integer livrableId);}
