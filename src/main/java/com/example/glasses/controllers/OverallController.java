package com.example.glasses.controllers;

import com.example.glasses.dto.GlassDto;
import com.example.glasses.repositories.GlassRepository;
import com.example.glasses.services.GlassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class OverallController {
    private final GlassService glassService;
    @Autowired
    public OverallController (GlassService glassService) {
        this.glassService = glassService;
    }
    @GetMapping("/overall")
    public String home(Model model, @RequestParam(value = "sort", required = false) String sort) {
        List<GlassDto> glasses;

        if ("asc".equals(sort)) {
            glasses = glassService.getGlassesSortedByStock(true);
        } else if ("desc".equals(sort)) {
            glasses = glassService.getGlassesSortedByStock(false);
        } else {
            glasses = glassService.getOverallGlassData();
        }
        long availableModelsCount = glassService.getAvailableModelCount();

        model.addAttribute("glasses", glasses);
        model.addAttribute("availableModelsCount", availableModelsCount);
        return "overall";
    }
}

