// ReqRes.java
package com.example.demo.dto;

import com.example.demo.model.Utilisateur;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String role;
    private String password;
    private String adresseEmail;
    private String nom;
    private String prenom;

    // Nouveaux champs pour l'héritage
    private String specialite;
    private Long departementId;
    private Long filiereId;
    private Long groupeId;

    private Utilisateur utilisateur;
    private List<Utilisateur> utilisateurList;
}