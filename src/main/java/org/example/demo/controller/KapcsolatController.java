package org.example.demo.controller;

import jakarta.validation.Valid;
import org.example.demo.entity.Uzenet;
import org.example.demo.repository.UzenetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class KapcsolatController {

    private final UzenetRepository uzenetRepository;

    public KapcsolatController(UzenetRepository uzenetRepository) {
        this.uzenetRepository = uzenetRepository;
    }

    @GetMapping("/kapcsolat")
    public String urlap(Model model) {
        model.addAttribute("title", "Kapcsolat");
        model.addAttribute("active", "kapcsolat");
        model.addAttribute("urlap", new Uzenet());
        return "kapcsolat";
    }

    @PostMapping("/kapcsolat")
    public String kuldes(@Valid @ModelAttribute("urlap") Uzenet uzenet,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {

            model.addAttribute("title", "Kapcsolat");
            model.addAttribute("active", "kapcsolat");
            return "kapcsolat";
        }
        uzenetRepository.save(uzenet);
        return "redirect:/kapcsolat?sent";
    }
}
