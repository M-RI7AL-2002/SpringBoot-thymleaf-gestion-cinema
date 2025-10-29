package com.example.GestionCinema.controllers;

import com.example.GestionCinema.entities.Billet;
import com.example.GestionCinema.entities.Film;
import com.example.GestionCinema.entities.Seance;
import com.example.GestionCinema.repositories.BilletRepository;
import com.example.GestionCinema.repositories.FilmRepository;
import com.example.GestionCinema.repositories.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private BilletRepository billetRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // --- Basic statistics ---
        long totalFilms = filmRepository.count();
        long totalSeances = seanceRepository.count();
        long totalBillets = billetRepository.count();

        model.addAttribute("activePage", "dashboard");

        double totalRevenue = billetRepository.findAll().stream()
                .filter(b -> b.getPrix() != null)
                .mapToDouble(Billet::getPrix)
                .sum();

        model.addAttribute("totalFilms", totalFilms);
        model.addAttribute("totalSeances", totalSeances);
        model.addAttribute("totalBillets", totalBillets);
        model.addAttribute("revenusTotal", String.format("%.2f", totalRevenue));

        // --- Recent activity (last 5 items) ---
        List<Seance> recentSeances = seanceRepository.findAll().stream()
                .sorted(Comparator.comparing(Seance::getDateHeure).reversed())
                .limit(5)
                .collect(Collectors.toList());

        List<Billet> recentBillets = billetRepository.findAll().stream()
                .sorted(Comparator.comparing(Billet::getDateAchat).reversed())
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("recentSeances", recentSeances);
        model.addAttribute("recentBillets", recentBillets);

        // --- Revenus chart (last 7 days) ---
        Map<String, Double> revenusStats = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            String dayLabel = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.FRANCE);

            double dailyRevenue = billetRepository.findAll().stream()
                    .filter(b -> b.getDateAchat() != null && b.getDateAchat().isEqual(date))
                    .mapToDouble(b -> b.getPrix() != null ? b.getPrix() : 0)
                    .sum();

            revenusStats.put(dayLabel, dailyRevenue);
        }
        model.addAttribute("revenusStats", revenusStats);

        // --- Top 5 films by tickets sold ---
        Map<String, Long> topFilmsStats = billetRepository.findAll().stream()
                .filter(b -> b.getSeance() != null && b.getSeance().getFilm() != null)
                .collect(Collectors.groupingBy(b -> b.getSeance().getFilm().getTitre(), Collectors.counting()));

        // Sort descending and take top 5
        topFilmsStats = topFilmsStats.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
        model.addAttribute("topFilmsStats", topFilmsStats);

        return "dashboard";
    }
}
