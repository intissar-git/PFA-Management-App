package com.example.demo.service;

import com.example.demo.model.Bloc;
import com.example.demo.repository.BlocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlocService {

    @Autowired
    private BlocRepository blocRepository;

    public List<Bloc> getAllBlocs() {
        return blocRepository.findAll();
    }
}