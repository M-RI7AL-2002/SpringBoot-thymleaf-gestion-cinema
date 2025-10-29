package com.example.GestionCinema.repositories;

import com.example.GestionCinema.entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SeanceRepository extends JpaRepository<Seance,Long> {

    @Query("select s from Seance s where s.salle = :salle and s.dateHeure = :date")
    public List<Seance> findBySalleAndDate(@Param("salle") String salle, @Param("date") LocalDateTime date);
}
