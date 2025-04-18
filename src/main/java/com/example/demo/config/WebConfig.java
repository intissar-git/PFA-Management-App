package com.example.demo.config;  // Remplacez com.example.demo par votre propre package

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Autoriser toutes les requêtes
                .allowedOrigins("http://localhost:8081") // Remplacez par l'URL de votre frontend React
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Méthodes autorisées
                .allowedHeaders("*"); // Autoriser tous les en-têtes
    }
}
