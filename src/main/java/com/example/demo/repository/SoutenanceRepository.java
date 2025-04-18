package com.example.demo.repository;

import com.example.demo.model.Soutenance;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SoutenanceRepository extends JpaRepository<Soutenance, Long> {

}