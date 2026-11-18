package org.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Controller
public class RestOldalController {

    @GetMapping("/rest")
    public String rest(Model model) {
        model.addAttribute("title", "REST");
        model.addAttribute("active", "rest");

        model.addAttribute("baseUrl", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString());
        return "rest";
    }
}
