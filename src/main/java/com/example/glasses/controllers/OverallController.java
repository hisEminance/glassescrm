package com.example.glasses.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class OverallController {

    @GetMapping("/overall")
    public String home(Model model) {
        return "overall";
    }

}
