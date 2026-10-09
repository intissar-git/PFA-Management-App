package com.example.demo.service;

import com.example.demo.dto.PlatformStatusDTO;
import com.example.demo.model.PlatformStatus;
import com.example.demo.repository.PlatformStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PlatformStatusService {

    @Autowired
    private PlatformStatusRepository platformStatusRepository;

    public PlatformStatusDTO getPlatformStatus() {
        PlatformStatus status = platformStatusRepository.findPlatformStatus();
        return convertToDTO(status);
    }

    @Transactional
    public PlatformStatusDTO updatePlatformStatus(PlatformStatusDTO dto) {
        PlatformStatus status = platformStatusRepository.findPlatformStatus();

        status.setActive(dto.isActive());
        status.setMaintenanceMessage(dto.getMaintenanceMessage());
        status.setReactivationDate(dto.getReactivationDate());
        status.setSendNotifications(dto.isSendNotifications());

        PlatformStatus updatedStatus = platformStatusRepository.save(status);

        // Ici vous pourriez ajouter la logique pour envoyer des notifications si nécessaire
        if (dto.isSendNotifications()) {
            sendNotification(dto.isActive(), dto.getMaintenanceMessage());
        }

        return convertToDTO(updatedStatus);
    }

    @Transactional
    public PlatformStatusDTO togglePlatformStatus() {
        PlatformStatus status = platformStatusRepository.findPlatformStatus();
        status.setActive(!status.isActive());
        PlatformStatus updatedStatus = platformStatusRepository.save(status);
        return convertToDTO(updatedStatus);
    }

    private PlatformStatusDTO convertToDTO(PlatformStatus status) {
        PlatformStatusDTO dto = new PlatformStatusDTO();
        dto.setActive(status.isActive());
        dto.setMaintenanceMessage(status.getMaintenanceMessage());
        dto.setReactivationDate(status.getReactivationDate());
        dto.setSendNotifications(status.isSendNotifications());
        return dto;
    }

    private void sendNotification(boolean isActive, String message) {
        // Implémentez ici la logique d'envoi d'email
        // Cela pourrait utiliser JavaMailSender ou un service de notification externe
        System.out.println("Notification envoyée - Statut: " + (isActive ? "Actif" : "Inactif"));
        if (!isActive) {
            System.out.println("Message de maintenance: " + message);
        }
    }
}