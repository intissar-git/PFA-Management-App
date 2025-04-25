package com.example.demo.controller;

import com.example.demo.model.Soutenance;
import com.example.demo.model.Tache;
import com.example.demo.service.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // Autoriser React à faire des requêtes
public class PlaningSoutenanceController {

}

