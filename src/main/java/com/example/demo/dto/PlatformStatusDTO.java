package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.time.LocalDateTime;

public class PlatformStatusDTO {
    private boolean isActive;
    private String maintenanceMessage;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "UTC")
    private LocalDateTime reactivationDate;

    private boolean sendNotifications;

    // Constructeurs, getters et setters

    public PlatformStatusDTO() {
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getMaintenanceMessage() {
        return maintenanceMessage;
    }

    public void setMaintenanceMessage(String maintenanceMessage) {
        this.maintenanceMessage = maintenanceMessage;
    }

    public LocalDateTime getReactivationDate() {
        return reactivationDate;
    }

    public void setReactivationDate(LocalDateTime reactivationDate) {
        this.reactivationDate = reactivationDate;
    }

    public boolean isSendNotifications() {
        return sendNotifications;
    }

    public void setSendNotifications(boolean sendNotifications) {
        this.sendNotifications = sendNotifications;
    }

}