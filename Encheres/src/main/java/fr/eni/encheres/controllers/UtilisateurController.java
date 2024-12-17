package fr.eni.encheres.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UtilisateurController {
    @RequestMapping({"/", "/login"})
    public String getLogin(Model model) {
        model.addAttribute("body", "pages/utilisateurs/connexionUtilisateur");
        return "index";
    }

}

