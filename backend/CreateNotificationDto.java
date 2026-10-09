package com.example.demo.dto;

import lombok.Data;

@Data
public class CreateNotificationDto {
    private String textNotif;
    private Long userId;
}