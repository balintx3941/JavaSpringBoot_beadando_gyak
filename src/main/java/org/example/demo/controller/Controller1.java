package org.example.demo.controller;

import org.example.demo.repository.SutiRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controller1 {

    private final SutiRepository sutiRepository;

    public Controller1(SutiRepository sutiRepository) {
        this.sutiRepository = sutiRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Főoldal");
        model.addAttribute("active", "fooldal");

        model.addAttribute("sutiDb", sutiRepository.count());
        model.addAttribute("tipusDb", sutiRepository.findDistinctTipus().size());
        model.addAttribute("dijazottak", sutiRepository.findByDijazottTrueOrderByNev());
        return "fooldal";
    }
}
