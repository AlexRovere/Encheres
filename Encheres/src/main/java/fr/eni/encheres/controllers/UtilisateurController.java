package fr.eni.encheres.controllers;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dto.UserUpdatePasswordDto;
import fr.eni.encheres.security.CustomUserDetails;
import fr.eni.encheres.services.UtilisateurServiceImpl;
import fr.eni.encheres.services.interf.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Clock;
import java.util.Optional;

@Controller
public class UtilisateurController {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurController.class);
    private final UtilisateurService utilisateurService;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurServiceImpl utilisateurServiceImpl;
    private UserUpdatePasswordDto userUpdatePasswordDto;

    public UtilisateurController(UtilisateurService utilisateurService, PasswordEncoder passwordEncoder, UtilisateurServiceImpl utilisateurServiceImpl) {
        this.utilisateurService = utilisateurService;
        this.passwordEncoder = passwordEncoder;
        this.utilisateurServiceImpl = utilisateurServiceImpl;
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
    public String modifierMotDePasse(Model model){
        model.addAttribute("userUpdatePasswordDto", new UserUpdatePasswordDto());
        model.addAttribute("body", "pages/utilisateurs/motDePasseUtilisateur");
        return "index";
    }

    @PostMapping("/utilisateurs/modifier/motDePasse")
    public String saveModificationMotDePasse(Model model, @Valid @ModelAttribute("userUpdatePasswordDto") UserUpdatePasswordDto userUpdatePasswordDto,
                                             BindingResult result, @AuthenticationPrincipal CustomUserDetails user, RedirectAttributes redirectAttributes) {
        // S'assurer que l'utilisateur est connecté
        Optional<Utilisateur> utilisateurOpt = utilisateurService.getById(user.getNoUtilisateur());
        if (utilisateurOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Le profil est introuvable.");
            return "redirect:/articles";
        }
        // Vérification des erreurs dans le formulaire
        if (result.hasErrors()) {
            model.addAttribute("body", "pages/utilisateurs/motDePasseUtilisateur");
            return "index";
        }
        // Vérifier si les 2 mdp correspondent
        if(!userUpdatePasswordDto.getNouveauMotDePasse().equals(userUpdatePasswordDto.getConfirmationMotDePasse())) {
            result.rejectValue("nouveauMotDePasse", "error.confirmationMotDePasse", "Les mots de passe ne correspondent pas.");
            model.addAttribute("body", "pages/utilisateurs/motDePasseUtilisateur");
            return "index";
        }
        // Mettre à jour le mot de passe hashé dans la BDD
        Utilisateur utilisateur = utilisateurOpt.get();
        String motDePasseHashed = passwordEncoder.encode(userUpdatePasswordDto.getNouveauMotDePasse());
        utilisateur.setMotDePasse(motDePasseHashed);
        utilisateurService.update(utilisateur);

        redirectAttributes.addFlashAttribute("enregistrementReussi", "Mot de passe modifié avec succès.");
        return "redirect:/utilisateurs/modifier/" + utilisateur.getNoUtilisateur();
    }
}

