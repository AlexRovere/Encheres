package fr.eni.encheres.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UtilisateurController {
    @GetMapping({"/", "/login"})
    public String getLogin(Model model) {
        model.addAttribute("body", "pages/utilisateurs/connexionUtilisateur");
        return "index";
//        return "pages/utilisateurs/connexionUtilisateur";
    }
}
