package com.example.GestionCinema.repositories;

import com.example.GestionCinema.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query("select  f from Film f  where f.genre = :genre ")
    public List<Film> findByGenre(@Param("genre") String genre);

    @Query("SELECT f.genre, COUNT(f) FROM Film f GROUP BY f.genre")
    Collection<Object[]> CountFilmByGenre();

}
