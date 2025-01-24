package com.example.glasses.controllers;


import com.example.glasses.dto.GlassDto;
import com.example.glasses.entities.Glass;
import com.example.glasses.services.FinanceService;
import com.example.glasses.services.GlassService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/glasses")
@CrossOrigin(origins = "*")
public class GlassController {

    private final GlassService glassService;
    private final FinanceService financeService;

    @Autowired
    public GlassController(GlassService glassService, FinanceService financeService) {
        this.glassService = glassService;
        this.financeService = financeService;
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
        //TODO
        //цю тєму точно треба винести кудась за СОЛІД-ом, супер впдлу і горять сроки, потім всьо рефакторну
        if (glass.getDateOfSale() == null) {
            glass.setDateOfSale(LocalDate.now());
        }
        System.out.println("Запит на додавання: " + glass);
        System.out.println("Створення/Оновлення Glass: " + glass);
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
            System.out.println("Створення/Оновлення Glass: " + glass);
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
    @PutMapping("/{id}/sold")
    public ResponseEntity<?> updateSoldQuantity(@PathVariable Long id, @RequestBody GlassDto dto) {
        Optional<Glass> optionalGlass = glassService.findById(id);
        if (optionalGlass.isPresent()) {
            Glass glass = optionalGlass.get();

            int oldSoldQuantity = dto.getOldSoldQuantity();
            int newSoldQuantity = dto.getSoldQuantity();

            int soldAmount = newSoldQuantity - oldSoldQuantity;

            //todo
            // checking if there is possibility to sell smth (working badely)
            if (soldAmount < 0 || glass.getStockQuantity() < soldAmount) {
                return ResponseEntity.badRequest()
                        .body("Недостатньо товарів на складі або некоректна кількість проданих одиниць.");
            }
            glass.setSoldQuantity(newSoldQuantity);
            glass.setStockQuantity(glass.getStockQuantity() - soldAmount);
            glassService.save(glass);
            financeService.saveDailyRevenue();
            return ResponseEntity.ok(glass);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
