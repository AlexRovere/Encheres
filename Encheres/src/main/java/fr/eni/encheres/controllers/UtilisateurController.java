package fr.eni.encheres.controllers;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.services.interf.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        Optional<Utilisateur> utilisateurOptional = utilisateurService.getById(noUtilisateur);
        if (utilisateurOptional.isEmpty()) {
            return "redirect:/articles";
        }
        Utilisateur utilisateur = utilisateurOptional.get();
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("body", "pages/utilisateurs/detailUtilisateur");
        return "index";
    }

    @GetMapping("/utilisateurs/ajouter")
    public String afficherFormulaireEnregistrerUtilisateur(Model model) {
        Utilisateur utilisateur = new Utilisateur();
        model.addAttribute("afficherLienSupprimer", false);
        model.addAttribute("afficherLienModifierMdp", false);
        model.addAttribute("afficherMotDePasse", true);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("body", "pages/utilisateurs/enregistrerUtilisateur");
        return "index";
    }

    @PostMapping("/utilisateurs/ajouter")
    public String enregistrerUtilisateur(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        utilisateurService.add(utilisateur);
        return "redirect:/articles";
    }

    @GetMapping("/utilisateurs/modifier/{noUtilisateur}")
    // Quand l'utilisateur click sur modifier ça get pour rediriger vers enregistrerUtilisateur
    public String afficherFormModifierUtilisateur(@PathVariable int noUtilisateur, Model model) {
        Optional<Utilisateur> utilisateurOptional = utilisateurService.getById(noUtilisateur);
        if (utilisateurOptional.isEmpty()) {
            return "redirect:/articles";
        }
        model.addAttribute("afficherLienSupprimer", true);
        model.addAttribute("afficherLienModifierMdp", true);
        model.addAttribute("afficherMotDePasse", false);
        model.addAttribute("utilisateur", utilisateurOptional.get());
        model.addAttribute("body", "pages/utilisateurs/enregistrerUtilisateur");
        return "index";
    }

    // Modification sur enregistrerUtilisateur
    @PostMapping("/utilisateurs/modifier")
    public String modifierUtilisateur(Model model, Utilisateur utilisateur) {
        Optional<Utilisateur> utilisateurOpt = utilisateurService.getById(utilisateur.getNoUtilisateur());
        if (utilisateurOpt.isEmpty()) {
            return "redirect:/articles";
        }
        model.addAttribute("body", "pages/utilisateurs/enregistrerUtilisateur");
        utilisateurService.update(utilisateur);
        model.addAttribute("utilisateur", utilisateurOpt.get());
        return "redirect:/utilisateurs/detail/" + utilisateur.getNoUtilisateur();
    }

    // Suppression sur enregistrerUtilisateur
    @GetMapping("/utilisateurs/supprimer/{noUtilisateur}")
    public String supprimerUtilisateur(Model model, @PathVariable("noUtilisateur") int noUtilisateur, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Optional<Utilisateur> utilisateurOpt = utilisateurService.getById(noUtilisateur);
        if (utilisateurOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Le profil est introuvable.");
            return "redirect:/articles";
        }
        try {
            utilisateurService.delete(noUtilisateur);
            request.getSession().invalidate();
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression du profil");
            return "redirect:/articles";
        }
        return "redirect:/articles";
    }

    // Affichage de la page de modification du mdp
    @GetMapping("/utilisateurs/modifier/motDePasse")
    public String ModifierMotDePasse(Model model){
        model.addAttribute("body", "pages/utilisateurs/motDePasseUtilisateur");
        return "index";
    }
}

