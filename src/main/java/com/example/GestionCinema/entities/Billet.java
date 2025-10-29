package com.example.GestionCinema.entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Billet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double prix;

    private String status;

    private LocalDate dateAchat;

    @ManyToOne
    private Seance seance;

    public Billet() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public String getStatus() {
        return status;
    }
}
