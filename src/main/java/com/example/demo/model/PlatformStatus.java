package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "platform_status")
public class PlatformStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActive;
    private String maintenanceMessage;
    private LocalDateTime reactivationDate;
    private boolean sendNotifications;

    // Constructeurs, getters et setters

    public PlatformStatus() {
    }

    public PlatformStatus(boolean isActive, String maintenanceMessage, LocalDateTime reactivationDate, boolean sendNotifications) {
        this.isActive = isActive;
        this.maintenanceMessage = maintenanceMessage;
        this.reactivationDate = reactivationDate;
        this.sendNotifications = sendNotifications;
    }

    // Getters and Setters
    // ...
}