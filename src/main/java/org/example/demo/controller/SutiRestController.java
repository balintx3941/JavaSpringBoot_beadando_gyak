package org.example.demo.controller;

import jakarta.validation.Valid;
import org.example.demo.entity.Suti;
import org.example.demo.repository.SutiRepository;
import org.example.demo.service.SutiService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/sutik")
public class SutiRestController {

    private final SutiRepository sutiRepository;
    private final SutiService sutiService;

    public SutiRestController(SutiRepository sutiRepository, SutiService sutiService) {
        this.sutiRepository = sutiRepository;
        this.sutiService = sutiService;
    }


    @GetMapping
    public List<Suti> osszes() {
        return sutiRepository.findAll(Sort.by("id"));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Suti> egy(@PathVariable Integer id) {
        return sutiRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Suti> letrehoz(@Valid @RequestBody Suti suti) {
        suti.setId(null);
        Suti mentett = sutiRepository.save(suti);
        URI hely = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(mentett.getId()).toUri();
        return ResponseEntity.created(hely).body(mentett);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Suti> modosit(@PathVariable Integer id, @Valid @RequestBody Suti suti) {
        if (!sutiRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        suti.setId(id);
        return ResponseEntity.ok(sutiRepository.save(suti));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> torol(@PathVariable Integer id) {
        if (!sutiRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        sutiService.torol(id);
        return ResponseEntity.noContent().build();
    }
}
