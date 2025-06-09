package com.example.demo.controller;

import com.example.demo.model.Bloc;
import com.example.demo.service.BlocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bloc")
public class BlocController {

    @Autowired
    private BlocService blocService;

    @GetMapping
    public List<Bloc> getAllBlocs() {
        return blocService.getAllBlocs();
    }
}