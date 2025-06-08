package com.example.demo.repository;

import com.example.demo.model.PlatformStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformStatusRepository extends JpaRepository<PlatformStatus, Long> {

    // On suppose qu'il n'y aura qu'un seul enregistrement pour le statut de la plateforme
    default PlatformStatus findPlatformStatus() {
        return findAll().stream().findFirst().orElseGet(() -> {
            PlatformStatus defaultStatus = new PlatformStatus();
            defaultStatus.setActive(true);
            defaultStatus.setMaintenanceMessage("La plateforme est temporairement désactivée pour maintenance.");
            defaultStatus.setSendNotifications(true);
            return save(defaultStatus);
        });
    }
}