package org.example.demo.controller;

import org.example.demo.repository.UzenetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UzenetekController {

    private final UzenetRepository uzenetRepository;

    public UzenetekController(UzenetRepository uzenetRepository) {
        this.uzenetRepository = uzenetRepository;
    }


    @GetMapping("/uzenetek")
    public String uzenetek(Model model) {
        model.addAttribute("title", "Üzenetek");
        model.addAttribute("active", "uzenetek");
        model.addAttribute("uzenetek", uzenetRepository.findAllByOrderByKuldveDesc());
        return "uzenetek";
    }
}
