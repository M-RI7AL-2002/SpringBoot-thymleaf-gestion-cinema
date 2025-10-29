package com.example.GestionCinema.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateHeure;

    private String salle;

    private String version;

    @ManyToOne
    private Film film;


    @OneToMany(mappedBy = "seance",cascade = CascadeType.ALL)
    private List <Billet> billets;

    public Seance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public List<Billet> getBillets() {
        return billets;
    }

    public void setBillets(List<Billet> billets) {
        this.billets = billets;
    }
}
