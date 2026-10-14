package com.example.demo.repository;

import com.example.demo.model.Soutenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SoutenanceRepository extends JpaRepository<Soutenance, Long> {
    List<Soutenance> findByGroupeId(Long groupeId);
    List<Soutenance> findBySalle(String salle);
    List<Soutenance> findByDate(Date date);
    List<Soutenance> findByJurysId(Long juryId);
    boolean existsBySalleAndDateAndHeure(String salle, Date date, String heure);
}