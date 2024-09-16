package com.example.glasses.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProductDetailsController {
    @GetMapping("/details")
    public String home(Model model) {
        return "details";
    }

}
