package com.example.demo.service;

import com.example.demo.model.Soutenance;
import com.example.demo.repository.SoutenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoutenanceService {
    @Autowired
    private SoutenanceRepository soutenanceRepository;

    public Soutenance saveSoutenance(Soutenance soutenance) {
        return soutenanceRepository.save(soutenance);
    }

    public List<Soutenance> getAllSoutenances() {
        return soutenanceRepository.findAll();
    }

    public void deleteSoutenance(Long id) {
        soutenanceRepository.deleteById(id);
    }

    public List<Soutenance> getSoutenancesByGroupe(Long groupeId) {
        return soutenanceRepository.findByGroupeId(groupeId);
    }
}