package com.example.demo.service;

import com.example.demo.model.Salle;
import com.example.demo.repository.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalleService {

    @Autowired
    private SalleRepository salleRepository;

    public List<Salle> getSallesByBlocId(int blocId) {
        return salleRepository.findByBlocId(blocId);
    }
}