package com.example.demo.repository;

import com.example.demo.model.Notification;
import com.example.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUtilisateurOrderByDateCreationDesc(Utilisateur utilisateur);
    List<Notification> findByUtilisateurAndStatutOrderByDateCreationDesc(Utilisateur utilisateur, boolean statut);

    @Transactional
    @Modifying
    @Query("UPDATE Notification n SET n.statut = true WHERE n.utilisateur = :utilisateur AND n.statut = false")
    int markAllAsReadByUser(Utilisateur utilisateur);

    @Transactional
    void deleteByUtilisateurAndStatut(Utilisateur utilisateur, boolean statut);
}