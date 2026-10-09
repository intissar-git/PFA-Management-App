package com.example.demo.controller;

import com.example.demo.model.Livrable;
import com.example.demo.model.MessageLivrable;
import com.example.demo.model.Utilisateur;
import com.example.demo.repository.LivrablesRepository;
import com.example.demo.repository.MessageLivrableRepository;
import com.example.demo.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/messages-livrable")
public class MessageLivrableController {

    @Autowired
    private MessageLivrableRepository repository;

    @Autowired
    private LivrablesRepository livrableRepo;

    @Autowired
    private UtilisateurRepository utilisateurRepo;

    @GetMapping("/{livrableId}")
    public List<MessageLivrable> getMessages(@PathVariable Integer livrableId) {
        return repository.findByLivrableIdOrderByDateEnvoiAsc(livrableId);
    }

    @PostMapping
    public MessageLivrable envoyerMessage(@RequestBody MessageRequest request) {
        Livrable livrable = livrableRepo.findById(request.livrableId.intValue()).orElseThrow();
        Utilisateur auteur = utilisateurRepo.findById(request.auteurId).orElseThrow();

        MessageLivrable msg = new MessageLivrable();
        msg.setLivrable(livrable);
        msg.setAuteur(auteur);
        msg.setContenu(request.contenu);

        return repository.save(msg);
    }

    static class MessageRequest {
        public Long livrableId;
        public Long auteurId;
        public String contenu;

        // Getters et Setters
    }
}
