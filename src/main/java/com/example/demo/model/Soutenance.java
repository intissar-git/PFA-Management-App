package com.example.demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;




import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Soutenance {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "groupe_id", nullable = false)
    private Groupe groupe;

    private String salle;

    @Temporal(TemporalType.DATE)
    private Date dateSoutenance;

    @Temporal(TemporalType.TIME)
    private Date heure;
   /* // Sérialiser la liste de jurys en JSON
    public void setJuryList(List<String> juryList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.jury = objectMapper.writeValueAsString(juryList); // Convertit la liste en JSON
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    // Désérialiser le JSON en liste de jurys
    public List<String> getJuryList() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(this.jury, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class)); // Convertit le JSON en liste
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/
}
