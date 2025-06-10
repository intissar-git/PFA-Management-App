package com.example.demo.controller;

import com.example.demo.model.DepotRapport;
import com.example.demo.model.Filiere;
import com.example.demo.repository.DepotRapportRepository;
import com.example.demo.repository.FiliereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/depot-rapport")
@CrossOrigin(origins = "http://localhost:5173")
public class DepotRapportController {

    @Autowired
    private DepotRapportRepository depotRapportRepository;

    @Autowired
    private FiliereRepository filiereRepository;

    @GetMapping
    public List<DepotRapport> getAllDepots() {
        return depotRapportRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createDepot(@RequestBody DepotRapport depot) {
        Optional<Filiere> filiereOptional = filiereRepository.findById((long) depot.getFiliere().getId());
        if (filiereOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Filière non trouvée");
        }

        depot.setFiliere(filiereOptional.get());
        return ResponseEntity.ok(depotRapportRepository.save(depot));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepot(@PathVariable Long id, @RequestBody DepotRapport depot) {
        Optional<DepotRapport> existingDepot = depotRapportRepository.findById(id);
        if (existingDepot.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Filiere> filiereOptional = filiereRepository.findById((long) depot.getFiliere().getId());
        if (filiereOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Filière non trouvée");
        }

        DepotRapport updated = existingDepot.get();
        updated.setFiliere(filiereOptional.get());
        updated.setDescription(depot.getDescription());
        updated.setDate(depot.getDate());


        return ResponseEntity.ok(depotRapportRepository.save(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepot(@PathVariable Long id) {
        if (!depotRapportRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        depotRapportRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
