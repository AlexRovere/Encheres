package fr.eni.encheres.controllers;

import fr.eni.encheres.bo.ArticleVendu;
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

    @GetMapping("/encheres/ajouter")
    public String getAjouterEnchere(Model model) {
        ArticleVendu article = new ArticleVendu();
        model.addAttribute("body", "pages/encheres/enregistrerEnchere");
        model.addAttribute("article", article);
        return "index";
    }
}
