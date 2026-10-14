package com.example.demo.controller;

import com.example.demo.dto.PlatformStatusDTO;
import com.example.demo.service.PlatformStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/api/platform-status")
@CrossOrigin(origins = "http://localhost:3000")
public class PlatformStatusController {

    @Autowired
    private PlatformStatusService platformStatusService;

    @GetMapping
    public PlatformStatusDTO getPlatformStatus() {
        PlatformStatusDTO dto = platformStatusService.getPlatformStatus();
        // Conversion UTC vers LocalDateTime (si nécessaire)
        if (dto.getReactivationDate() != null) {
            dto.setReactivationDate(LocalDateTime.ofInstant(
                    dto.getReactivationDate().atZone(ZoneOffset.UTC).toInstant(),
                    ZoneOffset.UTC
            ));
        }
        return dto;
    }

    @PutMapping
    public PlatformStatusDTO updatePlatformStatus(@RequestBody PlatformStatusDTO dto) {
        // Conversion explicite en UTC
        if (dto.getReactivationDate() != null) {
            dto.setReactivationDate(
                    LocalDateTime.ofInstant(
                            dto.getReactivationDate().atZone(ZoneOffset.UTC).toInstant(),
                            ZoneOffset.UTC
                    )
            );
        }
        return platformStatusService.updatePlatformStatus(dto);
    }

    @PostMapping("/toggle")
    public PlatformStatusDTO togglePlatformStatus() {
        return platformStatusService.togglePlatformStatus();
    }
}