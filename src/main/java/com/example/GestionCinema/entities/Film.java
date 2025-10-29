package com.example.GestionCinema.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String genre;

    private Long dureeMin;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Seance> seances;


    public Film() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Long getDureeMin() {
        return dureeMin;
    }

    public void setDureeMin(Long dureeMin) {
        this.dureeMin = dureeMin;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Seance> getSeances() {
        return seances;
    }

    public void setSeances(List<Seance> seances) {
        this.seances = seances;
    }
}
