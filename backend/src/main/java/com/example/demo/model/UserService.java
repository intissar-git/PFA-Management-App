package com.example.demo.service;

import com.example.demo.model.Utilisateur;
import com.example.demo.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UtilisateurRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

    public void changePassword(String email,
                               String currentPassword,
                               String newPassword,
                               String confirmationPassword) {
        Utilisateur user = userRepository.findByAdresseEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Validation du mot de passe actuel
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Le mot de passe actuel est incorrect");
        }

        // Validation des nouveaux mots de passe
        if (!newPassword.equals(confirmationPassword)) {
            throw new RuntimeException("Les nouveaux mots de passe ne correspondent pas");
        }

        if (newPassword.equals(currentPassword)) {
            throw new RuntimeException("Le nouveau mot de passe doit être différent de l'actuel");
        }

        if (!isPasswordValid(newPassword)) {
            throw new RuntimeException("""
                Le mot de passe doit contenir:
                - Au moins 8 caractères
                - Une majuscule
                - Une minuscule
                - Un chiffre
                - Un caractère spécial
                """);
        }

        // Mise à jour du mot de passe
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Révoquer tous les tokens existants
        tokenService.revokeAllUserTokens(user);
    }

    private boolean isPasswordValid(String password) {
        return Pattern.compile(PASSWORD_PATTERN)
                .matcher(password)
                .matches();
    }
}