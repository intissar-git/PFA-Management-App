package com.example.demo.repository;

import com.example.demo.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalleRepository extends JpaRepository<Salle, Integer> {
    List<Salle> findByBlocId(int blocId);
}