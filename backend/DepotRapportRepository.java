package com.example.demo.repository;

import com.example.demo.model.DepotRapport;
import com.example.demo.model.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepotRapportRepository  extends JpaRepository<DepotRapport, Long> {
    List<DepotRapport> findByFiliereId(int filiereId);


}
