package com.example.demo.service;

import com.example.demo.model.Fichier;
import com.example.demo.model.Livrable;
import com.example.demo.repository.FichierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FichierService {

    private final FichierRepository fichierRepository;
    private final Path cheminDossier = Paths.get("C:\\Users\\Souad\\Desktop\\Fichier_Livrables");

    public Fichier enregistrerFichier(MultipartFile fichier, Livrable livrable) {
        try {
            Files.createDirectories(cheminDossier);

            String nomFichier = fichier.getOriginalFilename();
            Path cheminFichier = cheminDossier.resolve(nomFichier);
            Files.copy(fichier.getInputStream(), cheminFichier, StandardCopyOption.REPLACE_EXISTING);

            Fichier fichierEntity = Fichier.builder()
                    .nom(nomFichier)
                    .chemin(cheminFichier.toString())
                    .type(fichier.getContentType())
                    .taille(fichier.getSize())
                    .dateCreation(LocalDateTime.now())
                    .livrable(livrable)
                    .build();

            fichierEntity.setLivrable(livrable);
            livrable.setFichier(fichierEntity);

            return fichierRepository.save(fichierEntity);
             // << nécessaire pour lier les deux objets côté Java


        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du stockage du fichier", e);
        }
    }

    //supprimer un fichier
    public void supprimerFichierParLivrable(Livrable livrable) {
        Fichier fichier = fichierRepository.findByLivrable(livrable);
        if (fichier != null) {
            fichierRepository.delete(fichier);
        }
    }

    //récuperé le fichier d'un livrable
    public Fichier getFichierByDeliverableId(Integer livrableId) {
        return fichierRepository.findByLivrableId(livrableId)
                .orElseThrow(() -> new RuntimeException("Fichier non trouvé pour le livrable ID : " + livrableId));
    }

}
