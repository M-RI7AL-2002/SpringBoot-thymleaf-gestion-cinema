package com.example.GestionCinema.controllers;


import com.example.GestionCinema.entities.Billet;
import com.example.GestionCinema.entities.Seance;
import com.example.GestionCinema.repositories.BilletRepository;
import com.example.GestionCinema.repositories.SeanceRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/billet")
public class BilletController {
    @Autowired
    SeanceRepository seanceRepository;
    @Autowired
    BilletRepository billetRepository;


    @GetMapping("/all")
    public String all(Model model){
        List<Billet> billets = billetRepository.findAll();
        model.addAttribute("billets", billets);
        model.addAttribute("statuses", billetRepository.getStatus());
        model.addAttribute("billetCount", billets.size());
        return "billet"; // Thymeleaf template billet.html
    }

    // Show form to create a new billet
    @GetMapping("/new")
    public String newBilletForm(Model model){
        model.addAttribute("billet", new Billet());
        model.addAttribute("seances", seanceRepository.findAll());
        model.addAttribute("subtitle", "Vente d'un nouveau billet");
        return "billetForm"; // Thymeleaf template billetForm.html
    }



    // Delete a billet by id
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        Optional<Billet> billet = billetRepository.findById(id);
        billet.ifPresent(b -> billetRepository.delete(b));
        return "redirect:/billet/all";
    }

    // Show form to edit a billet
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model){
        Optional<Billet> billet = billetRepository.findById(id);
        if(billet.isPresent()){
            model.addAttribute("billet", billet.get());
            model.addAttribute("seances", seanceRepository.findAll());
            model.addAttribute("subtitle", "Modification des informations du billet");
            return "billetForm"; // reuse form for edit
        }
        return "redirect:/billet/all";
    }

    // View a single billet
    @GetMapping("/{id}")
    public String viewBillet(@PathVariable Long id, Model model){
        Optional<Billet> billet = billetRepository.findById(id);
        if(billet.isPresent()){
            model.addAttribute("billet", billet.get());
            return "billetDetails"; // Thymeleaf template billetDetails.html
        }
        return "redirect:/billet/all";
    }

    // Filter billets by status
    @GetMapping("/status")
    public String filterByStatus(@RequestParam String status, Model model){
        List<Billet> billets = billetRepository.findByStatus(status);
        model.addAttribute("billets", billets);
        model.addAttribute("statuses", billetRepository.getStatus());
        model.addAttribute("selectedStatus", status);
        return "billet";
    }

    @PostMapping("/save")
    public String saveBillet(@Valid Billet billet, @RequestParam("seance") Long seanceId) {
        Optional<Seance> seance = seanceRepository.findById(seanceId);
        seance.ifPresent(billet::setSeance);
        billetRepository.save(billet);
        return "redirect:/billet/all";
    }






}
