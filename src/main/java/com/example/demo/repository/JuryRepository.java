package com.example.demo.repository;

import com.example.demo.model.Jury;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface JuryRepository extends JpaRepository<Jury, Long> {

    // Récupérer les jurys

    default List<Jury> getAllJury() {
        return findAll();
    }
}

