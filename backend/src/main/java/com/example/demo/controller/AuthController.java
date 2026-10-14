package com.example.demo.controller;

import com.example.demo.dto.PasswordChangeRequest;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody PasswordChangeRequest request) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            userService.changePassword(
                    email,
                    request.getCurrentPassword(),
                    request.getNewPassword(),
                    request.getConfirmationPassword()
            );

            return ResponseEntity.ok().body(Map.of(
                    "success", true,
                    "message", "Mot de passe modifié avec succès. Toutes vos sessions ont été déconnectées."
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        }
    }
}