package com.example.glasses.controllers;


import com.example.glasses.entities.Glass;
import com.example.glasses.services.GlassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/glasses")
@CrossOrigin(origins = "*")
public class GlassController {

    private final GlassService glassService;

    @Autowired
    public GlassController(GlassService glassService) {
        this.glassService = glassService;
    }

    @GetMapping
    public List<Glass> getAllGlasses() {
        return glassService.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Glass> getGlassById(@PathVariable Long id) {
        Optional<Glass> glass = glassService.findById(id);
        return glass.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Glass createGlass(@RequestBody Glass glass) {
        System.out.println("Запит на додавання: " + glass);
        return glassService.save(glass);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Glass> updateGlass(@PathVariable Long id, @RequestBody Glass updatedGlass) {
        Optional<Glass> optionalGlass = glassService.findById(id);
        if (optionalGlass.isPresent()) {
            Glass glass = optionalGlass.get();
            glass.setModelName(updatedGlass.getModelName());
            glass.setPurchasePrice(updatedGlass.getPurchasePrice());
            glass.setSalePrice(updatedGlass.getSalePrice());
            glass.setStockQuantity(updatedGlass.getStockQuantity());
            glass.setSoldQuantity(updatedGlass.getSoldQuantity());
            glass.setImageUrl(updatedGlass.getImageUrl());
            glass.setMarkupPercentage(updatedGlass.getMarkupPercentage());
            return ResponseEntity.ok(glassService.save(glass));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGlass(@PathVariable Long id) {
        if (glassService.findById(id).isPresent()) {
            glassService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
   }
}
