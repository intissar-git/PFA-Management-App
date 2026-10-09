package com.example.demo.controller;

import com.example.demo.model.Jury;
import com.example.demo.service.JuryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jury")
public class JuryController {

    @Autowired
    private JuryService juryService;

    @GetMapping
    public List<Jury> getAllJurys() {
        return juryService.getAllJury();
    }
}