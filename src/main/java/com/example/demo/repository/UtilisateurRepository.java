package com.example.demo.repository;


import com.example.demo.model.Encadrant;
import com.example.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    @Modifying
    @Query("UPDATE Utilisateur u SET u.password = :password  WHERE u.id = :id")
    void updateCodePfe(@Param("id") Long id, @Param("password ") String password);
    Optional<Utilisateur> findByAdresseEmail(String email);
    List<Utilisateur> findByRole(String role);

}
