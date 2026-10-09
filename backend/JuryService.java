package com.example.demo.service;

import com.example.demo.model.Jury;
import com.example.demo.repository.JuryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JuryService {
    //recuperer tout les jury
    @Autowired
    public JuryRepository juryRepository;
    public List<Jury> getAllJury(){
        return juryRepository.getAllJury();
    }
}
