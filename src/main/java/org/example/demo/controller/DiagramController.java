package org.example.demo.controller;

import org.example.demo.repository.SutiRepository;
import org.example.demo.repository.TartalomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DiagramController {

    private final SutiRepository sutiRepository;
    private final TartalomRepository tartalomRepository;

    public DiagramController(SutiRepository sutiRepository, TartalomRepository tartalomRepository) {
        this.sutiRepository = sutiRepository;
        this.tartalomRepository = tartalomRepository;
    }

    @GetMapping("/diagram")
    public String diagram(Model model) {

        List<String> tipusCimkek = new ArrayList<>();
        List<Long> tipusErtekek = new ArrayList<>();
        for (Object[] sor : sutiRepository.darabTipusSzerint()) {
            tipusCimkek.add((String) sor[0]);
            tipusErtekek.add((Long) sor[1]);
        }


        List<String> mentesCimkek = new ArrayList<>();
        List<Long> mentesErtekek = new ArrayList<>();
        for (Object[] sor : tartalomRepository.darabMentesSzerint()) {
            mentesCimkek.add((String) sor[0]);
            mentesErtekek.add((Long) sor[1]);
        }

        model.addAttribute("title", "Diagram");
        model.addAttribute("active", "diagram");
        model.addAttribute("tipusCimkek", tipusCimkek);
        model.addAttribute("tipusErtekek", tipusErtekek);
        model.addAttribute("mentesCimkek", mentesCimkek);
        model.addAttribute("mentesErtekek", mentesErtekek);
        return "diagram";
    }
}
