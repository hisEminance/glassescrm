package com.example.glasses.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class OverallController {
    @GetMapping("/overall")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Загальна інформація про товар");
        model.addAttribute("title", "Загальна інформація про товар");
        model.addAttribute("description", "Тут буде загальна інформація про товари.");
        return "overall";
    }
}
