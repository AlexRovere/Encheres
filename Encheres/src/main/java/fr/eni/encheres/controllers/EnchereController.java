package fr.eni.encheres.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnchereController {
    @GetMapping("/encheres")
    public String getEnchere(Model model) {
        model.addAttribute("body", "pages/encheres/listeEnchere");
        return "index";
    }
}
