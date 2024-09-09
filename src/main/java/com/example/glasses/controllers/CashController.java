package com.example.glasses.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class CashController {

    @GetMapping("/money")
    public String home(Model model) {
        return "money";
    }

}
