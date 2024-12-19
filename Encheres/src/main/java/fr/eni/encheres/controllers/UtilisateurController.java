package fr.eni.encheres.controllers;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exceptions.DatabaseException;
import fr.eni.encheres.services.interf.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;


@Controller
public class UtilisateurController {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurController.class);

    private final UtilisateurService utilisateurService;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurController(UtilisateurService utilisateurService, PasswordEncoder passwordEncoder) {
        this.utilisateurService = utilisateurService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping({"/", "/login"})
    public String getLogin(Model model) {
        model.addAttribute("body", "pages/utilisateurs/connexionUtilisateur");
        return "index";
    }

    @GetMapping("/utilisateurs/detail/{noUtilisateur}")
    public String detailUtilisateur(Model model, @PathVariable int noUtilisateur) {
        try {
            Optional<Utilisateur> utilisateur = utilisateurService.getById(noUtilisateur);
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("body", "pages/utilisateurs/detailUtilisateur");
        } catch (DatabaseException e) {
            logger.debug(e.getMessage() + e.getCause());
            return "redirect:/articles";
        }
        return "index";
    }

    @GetMapping("/utilisateurs/ajouter")
    public String afficherFormulaireEnregistrerUtilisateur(Model model) {
        Utilisateur utilisateur = new Utilisateur();
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("body", "pages/utilisateurs/enregistrerUtilisateur");
        return "index";
    }

    @PostMapping("/utilisateurs/ajouter")
    public String enregistrerUtilisateur(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        try {
            utilisateurService.add(utilisateur);
        } catch (DatabaseException e) {
            logger.debug(e.getMessage() + e.getCause());
        }
        return "redirect:/articles";
    }
}

