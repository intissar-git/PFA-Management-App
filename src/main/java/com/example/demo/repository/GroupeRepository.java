package com.example.demo.repository;
import com.example.demo.model.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository

public interface GroupeRepository extends JpaRepository<Groupe, Long> {

        // Récupérer tous les Groupes

             default List<Groupe> getAllGroupes() {
            return findAll();
        }

        // Sauvegarder un Groupe

             default Groupe saveGroupe(Groupe groupe) {
            return save(groupe);
        }

        //recuperer les Groupe d'une Filiere

            List<Groupe> findByFiliereId(int FiliereId);
             //recupere le nom du projet d'un groupe

                @Query("SELECT g.projet.titre FROM Groupe g WHERE g.id = :idGroupe")
                String findProjectTitleByGroupeId(@Param("idGroupe") int idGroupe);

    //recupere le nom d'encadrant par id du grouupe'

                @Query("SELECT g.encadrant.nom FROM Groupe g WHERE g.id = :idGroupe")
                String findEncadrantByGroupeId(@Param("idGroupe") int idGroupe);


    }

