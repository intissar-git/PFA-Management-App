package com.example.demo.service;

import com.example.demo.dto.ReqRes;
import com.example.demo.model.*; // Importer toutes les classes nécessaires du modèle
import com.example.demo.repository.*; // Importer tous les repositories nécessaires
import jakarta.persistence.EntityNotFoundException; // Utiliser une exception plus spécifique
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Assurer l'import correct de Transactional

import java.util.HashMap;
import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;
// import java.util.Optional; // Optional n'est pas explicitement utilisé ici mais peut l'être

@Service
public class UsersManagementService {

    // Injecter tous les repositories requis
    @Autowired private UtilisateurRepository utilisateurRepository;
    @Autowired private DepartementRepository departementRepository;
    @Autowired private FiliereRepository filiereRepository;
    // GroupeRepository est injecté mais non utilisé si aucun groupe par défaut n'est défini à l'inscription
    @Autowired private GroupeRepository groupeRepository;
    @Autowired private EncadrantRepository encadrantRepository; // Nécessaire pour getMyInfo/getUserById
    @Autowired private EtudiantRepository etudiantRepository;   // Nécessaire pour getMyInfo/getUserById

    // Injecter les composants de sécurité et utilitaires
    @Autowired private JWTUtils jwtUtils;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private PasswordEncoder passwordEncoder;


    /**
     * Enregistre un nouvel utilisateur (Admin, Encadrant ou Etudiant).
     * Gère la création de l'entité spécifique basée sur le rôle
     * et la sauvegarde via le repository parent.
     *
     * @param registrationRequest DTO contenant les informations d'inscription.
     * @return ReqRes DTO avec le résultat de l'opération.
     */
    @Transactional // Annotation Transactional pour assurer la cohérence des opérations DB
    public ReqRes register(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();
        try {
            // Validation simple du rôle (peut être étendue)
            if (registrationRequest.getRole() == null || registrationRequest.getRole().isBlank()) {
                throw new IllegalArgumentException("Le rôle utilisateur est requis.");
            }
            String role = registrationRequest.getRole().toUpperCase(); // Standardiser le rôle en majuscules
            Utilisateur utilisateur; // Déclarer en tant que type de base

            switch (role) {
                case "ENCADRANT":
                    if (registrationRequest.getDepartementId() == null || registrationRequest.getSpecialite() == null || registrationRequest.getSpecialite().isBlank()) {
                        throw new IllegalArgumentException("L'ID du département et la spécialité sont requis pour un encadrant.");
                    }
                    Encadrant encadrant = new Encadrant();
                    setCommonUserFields(encadrant, registrationRequest); // Définir les champs communs
                    encadrant.setSpecialite(registrationRequest.getSpecialite());

                    // Récupérer et associer le département
                    Departement departement = departementRepository.findById(registrationRequest.getDepartementId())
                            .orElseThrow(() -> new EntityNotFoundException("Département non trouvé avec l'ID : " + registrationRequest.getDepartementId()));
                    encadrant.setDepartement(departement);

                    utilisateur = encadrant; // Assigner à la variable de type base
                    break;

                case "ETUDIANT":
                    if (registrationRequest.getFiliereId() == null) {
                        throw new IllegalArgumentException("L'ID de la filière est requis pour un étudiant.");
                    }
                    Etudiant etudiant = new Etudiant();
                    setCommonUserFields(etudiant, registrationRequest); // Définir les champs communs

                    // Récupérer et associer la filière
                    Filiere filiere = filiereRepository.findById(registrationRequest.getFiliereId())
                            .orElseThrow(() -> new EntityNotFoundException("Filière non trouvée avec l'ID : " + registrationRequest.getFiliereId()));
                    etudiant.setFiliere(filiere);

                    // Le groupe n'est PAS défini ici. Il sera null car nullable=true dans l'entité.
                    // Il devra être assigné plus tard par un autre processus/endpoint.

                    utilisateur = etudiant; // Assigner à la variable de type base
                    break;

                case "ADMIN":
                    utilisateur = new Utilisateur(); // Créer un utilisateur de base
                    setCommonUserFields(utilisateur, registrationRequest); // Définir les champs communs
                    // Le rôle est déjà défini dans setCommonUserFields
                    break;

                default:
                    // Gérer les rôles non reconnus
                    throw new IllegalArgumentException("Rôle utilisateur invalide fourni : " + registrationRequest.getRole());
            }

            // Sauvegarde unique via le repository Utilisateur (JPA gère les tables jointes)
            Utilisateur savedUser = utilisateurRepository.save(utilisateur);

            // Préparer la réponse succès
            resp.setUtilisateur(savedUser); // Renvoyer l'utilisateur sauvegardé (avec son ID)
            resp.setStatusCode(200);
            resp.setMessage("Utilisateur enregistré avec succès");


        } catch (IllegalArgumentException | EntityNotFoundException e) {
            // Capturer les erreurs liées aux données d'entrée invalides ou entités non trouvées
            resp.setStatusCode(400); // Bad Request
            resp.setError("Erreur de validation : " + e.getMessage());
        }
        catch (Exception e) {
            log.error("Erreur lors de l'enregistrement : ", e); // ← Ajoutez cette ligne
            resp.setStatusCode(500);
            resp.setError("Erreur interne : " + e.getMessage());
        }
        return resp;
    }

    /**
     * Méthode utilitaire pour définir les champs communs à tous les types d'utilisateurs.
     * @param utilisateur L'objet Utilisateur (ou sous-classe) à peupler.
     * @param request Le DTO ReqRes contenant les données source.
     */
    private void setCommonUserFields(Utilisateur utilisateur, ReqRes request) {
        // Valider les champs communs essentiels ici si nécessaire
        if (request.getNom() == null || request.getNom().isBlank() ||
                request.getPrenom() == null || request.getPrenom().isBlank() ||
                request.getAdresseEmail() == null || request.getAdresseEmail().isBlank() ||
                request.getPassword() == null || request.getPassword().isBlank() ||
                request.getRole() == null || request.getRole().isBlank()) {
            throw new IllegalArgumentException("Les informations de base (nom, prénom, email, mot de passe, rôle) sont requises.");
        }

        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setAdresseEmail(request.getAdresseEmail());
        // Encoder le mot de passe avant de le stocker
        utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        // Stocker le rôle en majuscules pour la cohérence
        utilisateur.setRole(request.getRole().toUpperCase());
    }


    /**
     * Authentifie un utilisateur et génère des tokens JWT.
     * @param loginRequest DTO contenant email et mot de passe.
     * @return ReqRes DTO avec les tokens et le résultat.
     */
    public ReqRes login(ReqRes loginRequest) {
        ReqRes response = new ReqRes();
        try {
            // Tenter l'authentification via Spring Security
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getAdresseEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Récupérer l'utilisateur après authentification réussie
            Utilisateur utilisateur = utilisateurRepository.findByAdresseEmail(loginRequest.getAdresseEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + loginRequest.getAdresseEmail()));

            // Générer les tokens JWT
            String jwt = jwtUtils.generateToken(utilisateur);
            String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), utilisateur);

            // Préparer la réponse succès
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24H"); // Indiquer la durée de validité
            response.setRole(utilisateur.getRole()); // Renvoyer le rôle de l'utilisateur
            response.setMessage("Authentification réussie");

        } catch (UsernameNotFoundException e) {
            response.setStatusCode(404); // Not Found
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            // Capturer les erreurs d'authentification (ex: mauvais mot de passe) ou autres
            response.setStatusCode(401); // Unauthorized (ou 500 pour des erreurs inattendues)
            response.setMessage("Échec de l'authentification : Identifiants invalides ou erreur interne. " /*+ e.getMessage()*/); // Éviter de révéler trop de détails
        }
        return response;
    }

    /**
     * Rafraîchit un token JWT expiré en utilisant un refresh token valide.
     * @param refreshTokenRequest DTO contenant le refresh token.
     * @return ReqRes DTO avec le nouveau token JWT ou une erreur.
     */
    public ReqRes refreshToken(ReqRes refreshTokenRequest) {
        ReqRes response = new ReqRes();
        try {
            String email = jwtUtils.extractUsername(refreshTokenRequest.getToken()); // Le refresh token est passé dans 'token'
            Utilisateur utilisateur = utilisateurRepository.findByAdresseEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé pour le refresh token"));

            // Valider le refresh token
            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), utilisateur)) {
                // Générer un nouveau token d'accès (JWT)
                String newJwt = jwtUtils.generateToken(utilisateur);

                response.setStatusCode(200);
                response.setToken(newJwt); // Nouveau token d'accès
                // On ne renvoie généralement pas le refresh token à nouveau ici, sauf s'il est aussi renouvelé
                response.setRefreshToken(refreshTokenRequest.getToken()); // Renvoyer l'ancien refresh token (s'il est toujours valide)
                response.setExpirationTime("24H"); // Expiration du nouveau token
                response.setMessage("Token actualisé avec succès");
            } else {
                // Si le refresh token lui-même est invalide ou expiré
                response.setStatusCode(401);
                response.setMessage("Refresh token invalide ou expiré.");
            }
        } catch (UsernameNotFoundException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch (Exception e) {
            // Gérer d'autres erreurs potentielles lors de la validation/extraction du token
            response.setStatusCode(500);
            response.setMessage("Erreur lors de l'actualisation du token: " + e.getMessage());
        }
        return response;
    }


    /**
     * Récupère la liste de tous les utilisateurs.
     * @return ReqRes DTO contenant la liste des utilisateurs.
     */
    @Transactional(readOnly = true) // Optimisation pour les lectures seules
    public ReqRes getAllUtilisateurs() {
        ReqRes reqRes = new ReqRes();
        try {
            List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
            if (utilisateurs.isEmpty()) {
                reqRes.setStatusCode(404); // Not Found si aucun utilisateur
                reqRes.setMessage("Aucun utilisateur trouvé.");
            } else {
                reqRes.setUtilisateurList(utilisateurs);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Liste des utilisateurs récupérée avec succès.");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
        return reqRes;
    }

    /**
     * Récupère les informations d'un utilisateur spécifique par son ID.
     * Charge potentiellement les données spécifiques (Encadrant/Etudiant).
     * @param userId L'ID de l'utilisateur à récupérer.
     * @return ReqRes DTO contenant les informations de l'utilisateur.
     */
    @Transactional(readOnly = true)
    public ReqRes getUserById(Long userId) { // Utiliser Long pour l'ID
        ReqRes reqRes = new ReqRes();
        try {
            Utilisateur utilisateur = utilisateurRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + userId));

            // La nécessité de recharger via les repos spécifiques dépend de vos stratégies de Fetch.
            // Si les relations sont LAZY, cela peut être nécessaire pour obtenir toutes les données.
            // Si EAGER ou si déjà dans la session, findById peut suffire.
            // Cette approche est plus sûre pour garantir le chargement complet.
            if (utilisateur instanceof Encadrant) {
                // Optionnel: Recharger pour assurer que les relations LAZY sont chargées.
                utilisateur = encadrantRepository.findById(utilisateur.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Détails Encadrant non trouvés pour ID: " + userId));
            } else if (utilisateur instanceof Etudiant) {
                // Optionnel: Recharger pour assurer que les relations LAZY sont chargées.
                utilisateur = etudiantRepository.findById(utilisateur.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Détails Etudiant non trouvés pour ID: " + userId));
            }

            reqRes.setUtilisateur(utilisateur);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Utilisateur trouvé avec succès.");

        } catch (EntityNotFoundException e) {
            reqRes.setStatusCode(404); // Not Found
            reqRes.setError(e.getMessage());
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setError("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
        return reqRes;
    }


    /**
     * Met à jour les informations d'un utilisateur existant.
     * Gère la mise à jour des champs communs et spécifiques (Encadrant/Etudiant).
     * @param userId L'ID de l'utilisateur à mettre à jour.
     * @param updateRequest DTO ReqRes contenant les nouvelles informations.
     * @return ReqRes DTO avec le résultat de l'opération.
     */
    @Transactional
    public ReqRes updateEmployee(Long userId, ReqRes updateRequest) { // Utiliser Long pour l'ID
        ReqRes reqRes = new ReqRes();
        try {
            // Récupérer l'utilisateur existant
            Utilisateur existing = utilisateurRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Utilisateur à mettre à jour non trouvé avec l'ID : " + userId));

            // Mise à jour des champs communs (vérifier si les valeurs sont fournies dans la requête)
            if (updateRequest.getNom() != null) existing.setNom(updateRequest.getNom());
            if (updateRequest.getPrenom() != null) existing.setPrenom(updateRequest.getPrenom());
            if (updateRequest.getAdresseEmail() != null) existing.setAdresseEmail(updateRequest.getAdresseEmail());
            // Mettre à jour le mot de passe seulement s'il est fourni et non vide
            if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
                existing.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
            }
            // Note: Changer le rôle d'un utilisateur existant peut être complexe et nécessiter
            // la suppression/recréation dans les tables jointes. Non géré ici par défaut.

            // Mise à jour des champs spécifiques si l'utilisateur est du bon type
            if (existing instanceof Encadrant) {
                Encadrant encadrant = (Encadrant) existing;
                if (updateRequest.getSpecialite() != null) {
                    encadrant.setSpecialite(updateRequest.getSpecialite());
                }
                if (updateRequest.getDepartementId() != null) {
                    Departement departement = departementRepository.findById(updateRequest.getDepartementId())
                            .orElseThrow(() -> new EntityNotFoundException("Département non trouvé pour mise à jour : " + updateRequest.getDepartementId()));
                    encadrant.setDepartement(departement);
                }
            } else if (existing instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) existing;
                if (updateRequest.getFiliereId() != null) {
                    Filiere filiere = filiereRepository.findById(updateRequest.getFiliereId())
                            .orElseThrow(() -> new EntityNotFoundException("Filière non trouvée pour mise à jour : " + updateRequest.getFiliereId()));
                    etudiant.setFiliere(filiere);
                }
                // Ajouter ici la logique pour mettre à jour le groupe si nécessaire,
                // en récupérant le Groupe par ID depuis updateRequest si un groupeId est ajouté au DTO.
                // Exemple:
                // if (updateRequest.getGroupeId() != null) {
                //    Groupe groupe = groupeRepository.findById(updateRequest.getGroupeId()).orElse(null); // ou orElseThrow
                //    etudiant.setGroupe(groupe); // Permettre null si le groupe est retiré
                // }
            }

            // Sauvegarder les modifications (JPA gère la mise à jour)
            Utilisateur updatedUser = utilisateurRepository.save(existing);

            reqRes.setUtilisateur(updatedUser); // Renvoyer l'utilisateur mis à jour
            reqRes.setStatusCode(200);
            reqRes.setMessage("Utilisateur mis à jour avec succès.");

        } catch (EntityNotFoundException e) {
            reqRes.setStatusCode(404); // Not Found
            reqRes.setError(e.getMessage());
        } catch (IllegalArgumentException e) { // Pour les erreurs de validation potentielles
            reqRes.setStatusCode(400); // Bad Request
            reqRes.setError(e.getMessage());
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setError("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
        }
        return reqRes;
    }


    /**
     * Supprime un utilisateur par son ID.
     * @param userId L'ID de l'utilisateur à supprimer.
     * @return ReqRes DTO indiquant le résultat de l'opération.
     */
    @Transactional
    public ReqRes deleteEmployees(Long userId) { // Utiliser Long pour l'ID
        ReqRes reqRes = new ReqRes();
        try {
            // Vérifier si l'utilisateur existe avant de supprimer (optionnel mais recommandé)
            if (!utilisateurRepository.existsById(userId)) {
                throw new EntityNotFoundException("Utilisateur à supprimer non trouvé avec l'ID : " + userId);
            }
            utilisateurRepository.deleteById(userId); // La suppression en cascade dépend de la configuration JPA/DB
            reqRes.setStatusCode(200);
            reqRes.setMessage("Utilisateur supprimé avec succès.");
        } catch (EntityNotFoundException e) {
            reqRes.setStatusCode(404); // Not Found
            reqRes.setMessage(e.getMessage());
        }
        catch (Exception e) {
            // Gérer les erreurs potentielles (ex: contraintes de clé étrangère si la cascade n'est pas configurée)
            reqRes.setStatusCode(500);
            reqRes.setMessage("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        }
        return reqRes;
    }


    /**
     * Récupère les informations de l'utilisateur actuellement authentifié (via son email).
     * Charge potentiellement les données spécifiques (Encadrant/Etudiant).
     * @param email L'email (username) de l'utilisateur authentifié.
     * @return ReqRes DTO contenant les informations du profil.
     */
    @Transactional(readOnly = true)
    public ReqRes getMyInfo(String email) {
        ReqRes reqRes = new ReqRes();
        try {
            Utilisateur utilisateur = utilisateurRepository.findByAdresseEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé pour l'email : " + email));

            // Optionnel: Recharger via repo spécifique pour assurer le chargement complet (si relations LAZY)
            if (utilisateur instanceof Encadrant) {
                utilisateur = encadrantRepository.findById(utilisateur.getId()).orElseThrow(() -> new EntityNotFoundException("Détails Encadrant non trouvés"));
            } else if (utilisateur instanceof Etudiant) {
                utilisateur = etudiantRepository.findById(utilisateur.getId()).orElseThrow(() -> new EntityNotFoundException("Détails Etudiant non trouvés"));
            }

            reqRes.setUtilisateur(utilisateur); // Renvoyer l'objet utilisateur potentiellement enrichi
            reqRes.setStatusCode(200);
            reqRes.setMessage("Informations du profil récupérées avec succès.");

        } catch (UsernameNotFoundException | EntityNotFoundException e) {
            reqRes.setStatusCode(404); // Not Found
            reqRes.setMessage(e.getMessage());
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Erreur lors de la récupération du profil : " + e.getMessage());
        }
        return reqRes;
    }

    // La méthode createGroupe commentée peut être réactivée si nécessaire
    /* @Transactional
     public Groupe createGroupe(Groupe groupe) {
         // Ajouter validation ou logique métier ici si besoin
         return groupeRepository.save(groupe);
     }*/

    // La méthode getOrCreateDefaultGroupe commentée peut être utile si une logique de groupe par défaut est réintroduite
    /* private Groupe getOrCreateDefaultGroupe() {
         return groupeRepository.findByIntitule("Non assigné") // Assurez-vous que le champ 'intitule' existe dans Groupe
                 .orElseGet(() -> {
                     Groupe groupe = new Groupe();
                     groupe.setIntitule("Non assigné"); // Nom du groupe par défaut
                     // Définir d'autres propriétés si nécessaire
                     return groupeRepository.save(groupe);
                 });
     }*/
}