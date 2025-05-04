package com.example.demo.service;

import com.example.demo.model.Fichier;
import com.example.demo.repository.FichierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FichierService {

    private final FichierRepository fichierRepository;
    private final Path cheminDossier = Paths.get("C:\\Users\\Souad\\Desktop\\Fichier_Livrables");

    public Fichier enregistrerFichier(MultipartFile fichier) {
        try {
            Files.createDirectories(cheminDossier);

            String nomFichier = fichier.getOriginalFilename();
            Path cheminFichier = cheminDossier.resolve(nomFichier);
            Files.copy(fichier.getInputStream(), cheminFichier, StandardCopyOption.REPLACE_EXISTING);

            Fichier fichierEntity = new Fichier();
            fichierEntity.setNom(nomFichier);
            fichierEntity.setChemin(cheminFichier.toString());
            fichierEntity.setType(fichier.getContentType());
            fichierEntity.setTaille(fichier.getSize());

            return fichierRepository.save(fichierEntity);

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du stockage du fichier", e);
        }
    }
}
