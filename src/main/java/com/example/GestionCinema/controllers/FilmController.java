package com.example.GestionCinema.controllers;


import com.example.GestionCinema.entities.Film;
import com.example.GestionCinema.repositories.FilmRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/film")
public class FilmController {

    @Autowired
    FilmRepository filmRepository;

    @GetMapping("/all")
    public String all(Model model)throws com.fasterxml.jackson.core.JsonProcessingException {
        List<Film> films = filmRepository.findAll();
        model.addAttribute("films", films);

        // Stats
        Collection<Object[]> filmByGenre = filmRepository.CountFilmByGenre();
        model.addAttribute("filmCounts", filmRepository.count());

        // Convert genre stats to JSON safely
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        String filmByGenreJson = mapper.writeValueAsString(filmByGenre);
        model.addAttribute("filmByGenreJson", filmByGenreJson);

        return "film";}

    @PostMapping("/save")
    public String save(@Valid Film film , BindingResult result){
        filmRepository.save(film);
        return "redirect:/film/all";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id){

        filmRepository.delete(filmRepository.findById(id).get());
        return "redirect:/film/all";
    }

    @GetMapping("/new")
    public String newFilmForm(Model model){
        model.addAttribute("subtitle", "Ajout d'un nouveau film au catalogue");
        model.addAttribute("film", new Film());
        return "filmForm";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model){
        Optional<Film> film = filmRepository.findById(id);
        if(film.isPresent()){
            model.addAttribute("film", film.get());
            model.addAttribute("subtitle", "Modification des informations du film");
            return "filmForm";  // Reuse the same form for create and edit
        }
        return "redirect:/film/all";
    }

    @GetMapping("/genre")
    public String filmsByGenre(@RequestParam String genre, Model model)throws com.fasterxml.jackson.core.JsonProcessingException {
        List<Film> films = filmRepository.findByGenre(genre);
        model.addAttribute("films", films);

        // Always include stats
        Collection<Object[]> filmByGenre = filmRepository.CountFilmByGenre();
        model.addAttribute("filmByGenre", filmByGenre);
        model.addAttribute("filmCounts", filmRepository.count());
        model.addAttribute("selectedGenre", genre);

        // Convert for chart
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        String filmByGenreJson = mapper.writeValueAsString(filmByGenre);
        model.addAttribute("filmByGenreJson", filmByGenreJson);

        return "film";
    }


    // View a single film by id
    @GetMapping("/{id}")
    public String viewFilm(@PathVariable Long id, Model model){
        Optional<Film> film = filmRepository.findById(id);
        if(film.isPresent()){
            model.addAttribute("film", film.get());
            return "filmDetails"; // You need a Thymeleaf template named filmDetails.html
        }
        return "redirect:/film/all";
    }

}
