package com.example.demo.controller;

import com.example.demo.dto.ReqRes;
import com.example.demo.model.Groupe;
import com.example.demo.service.UsersManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UtilisateurManagementController {

    @Autowired
    private UsersManagementService usersManagementService;
    private static final Logger log = LoggerFactory.getLogger(UtilisateurManagementController.class);

    public void AuthController(UsersManagementService usersManagementService) {
        this.usersManagementService = usersManagementService;
    }

    public UtilisateurManagementController(UsersManagementService usersManagementService) {
        this.usersManagementService = usersManagementService;
    }

    @PostMapping("/register")
    public ResponseEntity<ReqRes> register(@RequestBody ReqRes reg) {
        try {
            ReqRes response = usersManagementService.register(reg);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Registration failed for email: {}", reg.getAdresseEmail(), e);

            // Return a structured error response
            ReqRes errorResponse = new ReqRes();
            errorResponse.setStatusCode(500);
            errorResponse.setError("Registration failed: " + e.getMessage());

            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
   /* // Endpoint pour créer un groupe (exemple)
    @PostMapping("/encadrant/create-groupe")
    public ResponseEntity<Groupe> createGroupe(@RequestBody Groupe groupe) {
        return ResponseEntity.ok(usersManagementService.createGroupe(groupe));
    }*/

    @PostMapping("/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req) {
        return ResponseEntity.ok(usersManagementService.login(req));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req) {
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<ReqRes> getAllUsers() {
        return ResponseEntity.ok(usersManagementService.getAllUtilisateurs());
    }

    @GetMapping("/admin/get-users/{userId}")
    public ResponseEntity<ReqRes> getUserByID(@PathVariable Integer userId) {
        return ResponseEntity.ok(usersManagementService.getUserById(Long.valueOf(userId)));
    }

    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<ReqRes> updateUser(
            @PathVariable Integer userId,
            @RequestBody ReqRes updateRequest) { // Changé de Utilisateur à ReqRes
        return ResponseEntity.ok(usersManagementService.updateEmployee(Long.valueOf(userId), updateRequest));
    }

    @GetMapping("/rh/get-profile")
    public ResponseEntity<ReqRes> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ReqRes response = usersManagementService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<ReqRes> deleteUser(@PathVariable long userId) {
        return ResponseEntity.ok(usersManagementService.deleteEmployees((userId)));
    }

    // Ajout d'une endpoint de déconnexion si nécessaire
    @PostMapping("/logout")
    public ResponseEntity<ReqRes> logout() {
        ReqRes response = new ReqRes();
        response.setStatusCode(200);
        response.setMessage("Déconnexion réussie");
        return ResponseEntity.ok(response);
    }
}