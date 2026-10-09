package com.example.demo.service;

import com.example.demo.model.Groupe;
import com.example.demo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetService {
    @Autowired
    public ProjectRepository projectRepository;

}
