package com.example.demo.controller;

import com.example.demo.dto.CreateNotificationDto;
import com.example.demo.model.Notification;
import com.example.demo.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody CreateNotificationDto dto) {
        return ResponseEntity.ok(notificationService.createNotification(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    @PutMapping("/user/{userId}/mark-read")
    public ResponseEntity<Void> markAllAsRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/{userId}/read")
    public ResponseEntity<Void> deleteReadNotifications(@PathVariable Long userId) {
        notificationService.deleteReadNotifications(userId);
        return ResponseEntity.ok().build();
    }
}