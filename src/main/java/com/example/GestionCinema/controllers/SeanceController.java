package com.example.GestionCinema.controllers;


import com.example.GestionCinema.entities.Film;
import com.example.GestionCinema.entities.Seance;
import com.example.GestionCinema.repositories.FilmRepository;
import com.example.GestionCinema.repositories.SeanceRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/seance")
public class SeanceController {
    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping("/all")
    public String all(Model model){
        List<Seance> seances = seanceRepository.findAll();
        model.addAttribute("seances", seances);
        model.addAttribute("seanceCount", seances.size());
        return "seance"; // Thymeleaf template seance.html
    }

    // Show form to create a new seance
    @GetMapping("/new")
    public String newSeanceForm(Model model){
        model.addAttribute("seance", new Seance());
        model.addAttribute("subtitle", "Programmation d'une nouvelle séance");
        model.addAttribute("films", filmRepository.findAll());
        return "seanceForm"; // Thymeleaf template seanceForm.html
    }

    // Save a new seance
    @PostMapping("/save")
    public String save(@Valid Seance seance, @RequestParam("film") Long filmId) {
        Optional<Film> film = filmRepository.findById(filmId);
        film.ifPresent(seance::setFilm);
        seanceRepository.save(seance);
        return "redirect:/seance/all";
    }


    // Delete a seance by id
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        Optional<Seance> seance = seanceRepository.findById(id);
        seance.ifPresent(s -> seanceRepository.delete(s));
        return "redirect:/seance/all";
    }

    // Show form to edit a seance
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model){
        Optional<Seance> seance = seanceRepository.findById(id);
        if(seance.isPresent()){
            model.addAttribute("seance", seance.get());
            model.addAttribute("films", filmRepository.findAll());
            model.addAttribute("subtitle", "Modification des informations de la séance");
            return "seanceForm"; // reuse form for edit
        }
        return "redirect:/seance/all";
    }

    // View a single seance
    @GetMapping("/{id}")
    public String viewSeance(@PathVariable Long id, Model model){
        Optional<Seance> seance = seanceRepository.findById(id);
        if (seance.isPresent()) {
            model.addAttribute("seance", seance.get());
            model.addAttribute("films", filmRepository.findAll());
            return "seanceDetails"; // Thymeleaf template
        }
        return "redirect:/seance/all";
    }

    // Filter seances by salle and date
    @GetMapping("/filter")
    public String filterSeances(@RequestParam String salle, @RequestParam LocalDateTime date, Model model ){

        List<Seance> seances = seanceRepository.findBySalleAndDate(salle, date);
        model.addAttribute("seances", seances);
        model.addAttribute("seanceCount", seances.size());
        model.addAttribute("selectedSalle", salle);
        model.addAttribute("selectedDate", date);
        return "seance";
    }

}
