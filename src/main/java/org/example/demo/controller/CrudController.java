package org.example.demo.controller;

import jakarta.validation.Valid;
import org.example.demo.entity.Suti;
import org.example.demo.repository.SutiRepository;
import org.example.demo.service.SutiService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.server.ResponseStatusException;


@Controller
@RequestMapping("/crud")
public class CrudController {

    private final SutiRepository sutiRepository;
    private final SutiService sutiService;

    public CrudController(SutiRepository sutiRepository, SutiService sutiService) {
        this.sutiRepository = sutiRepository;
        this.sutiService = sutiService;
    }


    @GetMapping
    public String lista(Model model) {
        model.addAttribute("title", "CRUD");
        model.addAttribute("active", "crud");
        model.addAttribute("sutik", sutiRepository.findAll(Sort.by("id")));
        return "crud-lista";
    }


    @GetMapping("/uj")
    public String ujUrlap(Model model) {
        model.addAttribute("suti", new Suti());
        urlapAdatok(model, "Új sütemény", "/crud/uj");
        return "crud-urlap";
    }


    @PostMapping("/uj")
    public String ujMentes(@Valid @ModelAttribute("suti") Suti suti, BindingResult result,
                           Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            urlapAdatok(model, "Új sütemény", "/crud/uj");
            return "crud-urlap";
        }
        suti.setId(null);
        sutiRepository.save(suti);
        redirect.addFlashAttribute("siker", "A sütemény hozzáadva: " + suti.getNev());
        return "redirect:/crud";
    }


    @GetMapping("/{id}/szerkesztes")
    public String szerkesztesUrlap(@PathVariable Integer id, Model model) {
        Suti suti = sutiRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("suti", suti);
        urlapAdatok(model, "Sütemény szerkesztése", "/crud/" + id + "/szerkesztes");
        return "crud-urlap";
    }


    @PostMapping("/{id}/szerkesztes")
    public String szerkesztesMentes(@PathVariable Integer id, @Valid @ModelAttribute("suti") Suti suti,
                                    BindingResult result, Model model, RedirectAttributes redirect) {
        if (!sutiRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (result.hasErrors()) {
            urlapAdatok(model, "Sütemény szerkesztése", "/crud/" + id + "/szerkesztes");
            return "crud-urlap";
        }
        suti.setId(id);
        sutiRepository.save(suti);
        redirect.addFlashAttribute("siker", "A sütemény módosítva: " + suti.getNev());
        return "redirect:/crud";
    }


    @PostMapping("/{id}/torles")
    public String torles(@PathVariable Integer id, RedirectAttributes redirect) {
        sutiService.torol(id);
        redirect.addFlashAttribute("siker", "A sütemény törölve (azonosító: " + id + ").");
        return "redirect:/crud";
    }

    private void urlapAdatok(Model model, String title, String urlapAction) {
        model.addAttribute("title", title);
        model.addAttribute("active", "crud");
        model.addAttribute("urlapAction", urlapAction);
        model.addAttribute("tipusok", sutiRepository.findDistinctTipus());
    }
}
