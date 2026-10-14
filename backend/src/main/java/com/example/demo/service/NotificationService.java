package com.example.demo.service;

import com.example.demo.dto.CreateNotificationDto;
import com.example.demo.model.Notification;
import com.example.demo.model.Utilisateur;
import com.example.demo.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UtilisateurService utilisateurService;

    public Notification createNotification(CreateNotificationDto dto) {
        Utilisateur utilisateur = utilisateurService.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Notification notification = Notification.builder()
                .textNotif(dto.getTextNotif())
                .utilisateur(utilisateur)
                .build();

        return notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(Long userId) {
        Utilisateur utilisateur = utilisateurService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return notificationRepository.findByUtilisateurOrderByDateCreationDesc(utilisateur);
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        notificationRepository.markAllAsReadByUser(userId);
    }

    @Transactional
    public void deleteReadNotifications(Long userId) {
        Utilisateur utilisateur = utilisateurService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        notificationRepository.deleteByUtilisateurAndStatut(utilisateur, true);
    }
}