package com.example.demo.service;

import com.example.demo.model.Departement;
import com.example.demo.repository.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartementService {
    @Autowired
    public DepartementRepository departementRepository;
//recupere tout
    public List<Departement> getAllDepartement(){
        return departementRepository.getAllDepartement();
    }
    //ajouter un
    public Departement addDepartement(Departement departement){
        return departementRepository.saveDepartement(departement);
    }
}
