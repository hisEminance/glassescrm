package com.example.glasses.services;

import com.example.glasses.entities.Glass;
import com.example.glasses.repositories.GlassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Glass> getAllLecturers() {
        return glassRepository.findAll();
    }

    public Glass save(Glass glass) {
        return glassRepository.save(glass);
    }

    public void deleteById(Long id) {
        glassRepository.deleteById(id);
    }
}

