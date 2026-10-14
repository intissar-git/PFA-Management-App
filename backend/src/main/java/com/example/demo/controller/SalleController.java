package com.example.demo.controller;

import com.example.demo.model.Salle;
import com.example.demo.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salle")
public class SalleController {

    @Autowired
    private SalleService salleService;

    @GetMapping("/byBloc/{blocId}")
    public List<Salle> getSallesByBloc(@PathVariable int blocId) {
        return salleService.getSallesByBlocId(blocId);
    }
}