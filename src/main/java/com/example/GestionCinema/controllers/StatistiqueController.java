package com.example.GestionCinema.controllers;

import com.example.GestionCinema.entities.Billet;
import com.example.GestionCinema.entities.Seance;
import com.example.GestionCinema.repositories.BilletRepository;
import com.example.GestionCinema.repositories.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StatistiqueController {

    @Autowired
    private BilletRepository billetRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @GetMapping("/statistique")
    public String statistique(Model model) {
        // Set active page for sidebar
        model.addAttribute("activePage", "stats");

        // Taux de remplissage par séance (mock data for now - you can enhance with real calculations)
        Map<String, Double> tauxRemplissage = new LinkedHashMap<>();
        List<Seance> seances = seanceRepository.findAll();
        
        for (Seance seance : seances) {
            String seanceLabel = seance.getFilm() != null ? 
                seance.getFilm().getTitre() + " - " + seance.getSalle() + " (" + 
                seance.getDateHeure().toLocalDate().toString() + ")" :
                "Séance " + seance.getId();
            
            // Calculate occupancy rate (mock calculation - you can enhance this)
            long billetCount = billetRepository.findAll().stream()
                .filter(b -> b.getSeance() != null && b.getSeance().getId().equals(seance.getId()))
                .count();
            
            // Assume each salle has 100 seats (you can make this dynamic)
            double taux = Math.min(100.0, (billetCount * 100.0) / 100.0);
            tauxRemplissage.put(seanceLabel, taux);
        }

        // Recettes par film
        Map<String, Double> recettesParFilm = billetRepository.findAll().stream()
            .filter(b -> b.getSeance() != null && b.getSeance().getFilm() != null && b.getPrix() != null)
            .collect(Collectors.groupingBy(
                b -> b.getSeance().getFilm().getTitre(),
                Collectors.summingDouble(Billet::getPrix)
            ));

        // Sort by revenue descending
        recettesParFilm = recettesParFilm.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));

        model.addAttribute("tauxRemplissage", tauxRemplissage);
        model.addAttribute("recettesParFilm", recettesParFilm);

        return "statistique";
    }
}
