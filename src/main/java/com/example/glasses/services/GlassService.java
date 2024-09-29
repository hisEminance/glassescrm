package com.example.glasses.services;

import com.example.glasses.dto.GlassDto;
import com.example.glasses.entities.Glass;
import com.example.glasses.repositories.GlassRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class GlassService {
    private final GlassRepository glassRepository;

    @Autowired
    public GlassService(GlassRepository glassRepository ) {
        this.glassRepository = glassRepository;
    }
    public List<Glass> findAll() {
        return glassRepository.findAll();
    }

    public Optional<Glass> findById(Long id) {
        return glassRepository.findById(id);
    }

    public Glass save(Glass glass) {
        return glassRepository.save(glass);
    }

    public void deleteById(Long id) {
        glassRepository.deleteById(id);
    }
    public List<GlassDto> getOverallGlassData() {
        List<Glass> glasses = glassRepository.findAll();
        List<GlassDto> result = new ArrayList<>();

        for (Glass glass : glasses) {
            BigDecimal restockingCost = glass.getPurchasePrice()
                    .multiply(BigDecimal.valueOf(glass.getIdealStock() - glass.getStockQuantity()));
            GlassDto dto = new GlassDto();
            dto.setModelName(glass.getModelName());
            dto.setStockQuantity(glass.getStockQuantity());
            dto.setSoldQuantity(glass.getSoldQuantity());
            dto.setProfit(glass.getProfit());
            dto.setReplenishmentCost(glass.getReplenishmentCost());
            dto.setRestockingCost(restockingCost);
            dto.setIdealStock(glass.getIdealStock());
            result.add(dto);
        }

        return result;
    }
    public List<GlassDto> getGlassesSortedByStock(boolean ascending) {
        List<GlassDto> overallData = getOverallGlassData();
        if (ascending) {
            overallData.sort(Comparator.comparingInt(GlassDto::getStockQuantity));
        } else {
            overallData.sort(Comparator.comparingInt(GlassDto::getStockQuantity).reversed());
        }
        return overallData;
    }

    public long getAvailableModelCount() {
        return glassRepository.findAll().stream()
                .filter(glass -> glass.getStockQuantity() > 0)
                .count();
    }

    // піздєц, метод криво працює
    public void changeSoldQuantity(Long id,  int newSoldQuantity, int oldSoldQuantity) {
        System.out.println("Old sold quantity: " + oldSoldQuantity);
        System.out.println("New sold quantity: " + newSoldQuantity);
        int difference = newSoldQuantity - oldSoldQuantity;
        System.out.println("Difference: " + difference);
        if (difference != 0) {
            Glass glass = glassRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Glass not found"));
            glass.setSoldQuantity(newSoldQuantity);
            glass.setStockQuantity(glass.getStockQuantity() - difference);
            glassRepository.save(glass);
        }
    }
}

