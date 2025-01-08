package fr.eni.encheres.controllers;

import fr.eni.encheres.bo.*;
import fr.eni.encheres.dto.EtatVente;
import fr.eni.encheres.dto.FilterDto;
import fr.eni.encheres.security.CustomUserDetails;
import fr.eni.encheres.services.CustomUserDetailsService;
import fr.eni.encheres.services.interf.ArticleService;
import fr.eni.encheres.services.interf.CategorieService;
import fr.eni.encheres.services.interf.UtilisateurService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;
    private final CategorieService categorieService;
    private final UtilisateurService utilisateurService;
    private final UserDetailsService userDetailsService;
    private final CustomUserDetailsService customUserDetailsService;

    public ArticleController(ArticleService articleService, CategorieService categorieService, UtilisateurService utilisateurService, UserDetailsService userDetailsService, CustomUserDetailsService customUserDetailsService) {
        this.articleService = articleService;
        this.categorieService = categorieService;
        this.utilisateurService = utilisateurService;
        this.userDetailsService = userDetailsService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/articles")
    public String getArticles(Model model, FilterDto filterDto) {
        model.addAttribute("categories", categorieService.getAll());
        model.addAttribute("filterDto", filterDto);
        model.addAttribute("articles", articleService.getAllWithFilters(filterDto));
        model.addAttribute("body", "pages/articles/listeArticle");
        return "index";
    }


    @GetMapping("/articles/detail/{noArticle}")
    public String getDetailArticle(Model model, @AuthenticationPrincipal CustomUserDetails user, @PathVariable("noArticle") int noArticle) {
        Optional<Article> articleOptional = articleService.getById(noArticle);
        if(articleOptional.isEmpty()) {
            return "redirect:/articles";
        }
        Article article = articleOptional.get();
        article.setEtatVente(articleService.getEtatVente(article));

        Integer noUtilisateur = null;

        // vérifier si t'es connecté
        if(user != null) {
            noUtilisateur = user.getNoUtilisateur();
        }

        model.addAttribute("noUtilisateur", noUtilisateur);
        model.addAttribute("enchere", articleService.getMeilleurEnchere(article));
        model.addAttribute("article", article);
        model.addAttribute("body", "pages/articles/detailArticle");
        return "index";
    }


    @GetMapping("/articles/ajouter")
    public String getAjouterArticle(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        Article article = new Article();
        article.setDateDebutEncheres(LocalDate.now());
        article.setDateFinEncheres(LocalDate.now().plusDays(1));

       Optional<Utilisateur> utilisateurOptional = utilisateurService.getById(user.getNoUtilisateur());
       if(utilisateurOptional.isEmpty()) {
           return "redirect:/articles";
       }

        article.setUtilisateur(utilisateurOptional.get());

        model.addAttribute("body", "pages/articles/enregistrerArticle");
        model.addAttribute("article", article);
        return "index";
    }

    @PostMapping("/articles/ajouter")
    public String postAjouterArticle(Model model, @AuthenticationPrincipal CustomUserDetails user, @Valid @ModelAttribute("article") Article article, BindingResult result, RedirectAttributes redirectAttr,
                                     @RequestParam("ville") String ville, @RequestParam("rue") String rue, @RequestParam("codePostal") String codePostal, @RequestParam("img") MultipartFile img) {
        Optional<Utilisateur> utilisateurOptional;
        utilisateurOptional = utilisateurService.getById(user.getNoUtilisateur());
        if(utilisateurOptional.isEmpty()) {
            return "redirect:/articles";
        }
        Retrait retrait = new Retrait(rue, codePostal, ville);
        article.setRetrait(retrait);
        article.setUtilisateur(utilisateurOptional.get());
        if(result.hasErrors()){
            model.addAttribute("body", "pages/articles/enregistrerArticle");
            return "index";
        }
        Image image = new Image();
        image.setNoArticle(article.getNoArticle());
        image.setMimeType(img.getContentType());
        image.setFileName(img.getOriginalFilename());
        try {
            image.setData(img.getBytes());
        } catch (IOException e) {
            logger.error("impossible d'upload l'image");
        }
        article.setImage(image);
        articleService.add(article);
        return "redirect:/articles";
    }

    @GetMapping("/articles/modifier/{noArticle}")
    public String getAjouterArticle(Model model, @AuthenticationPrincipal CustomUserDetails user, @PathVariable("noArticle") int id) {
        Optional<Article> articleOptional = articleService.getById(id);
        if(articleOptional.isEmpty()) {
            return "redirect:/articles";
        }

        Article article = articleOptional.get();

        if(user == null || article.getUtilisateur().getNoUtilisateur() != user.getNoUtilisateur()) {
            return "redirect:/articles";
        }

        article.setEtatVente(articleService.getEtatVente(article));

        model.addAttribute("body", "pages/articles/enregistrerArticle");
        model.addAttribute("article", article);
        return "index";
    }

    @PostMapping("/articles/modifier")
    public String postModifierArticle(Model model, @AuthenticationPrincipal CustomUserDetails user, @Valid @ModelAttribute("article") Article article, BindingResult result,  RedirectAttributes redirectAttr,
                                     @RequestParam("ville") String ville, @RequestParam("rue") String rue, @RequestParam("codePostal") String codePostal) {
        Optional<Utilisateur> utilisateurOptional;
        utilisateurOptional = utilisateurService.getById(user.getNoUtilisateur());
        if(utilisateurOptional.isEmpty()) {
            return "redirect:/articles";
        }
        Retrait retrait = new Retrait(rue, codePostal, ville);
        article.setRetrait(retrait);
        article.setUtilisateur(utilisateurOptional.get());
        if(result.hasErrors()){
            model.addAttribute("noArticle", article.getNoArticle());
            model.addAttribute("body", "pages/articles/enregistrerArticle");
            return "index";
        }
        articleService.update(article);
        return "redirect:/articles";
    }

    @GetMapping("/articles/supprimer/{noArticle}")
    public String supprimerArticle(Model model,@AuthenticationPrincipal CustomUserDetails user, @PathVariable("noArticle") int noArticle) {
        Optional<Article> articleOptional = articleService.getById(noArticle);
        if(articleOptional.isEmpty()) {
            return "redirect:/articles";
        }

        Article article = articleOptional.get();

        if(article.getUtilisateur().getNoUtilisateur() != user.getNoUtilisateur()) {
            return "redirect:/articles";
        }

        articleService.delete(article.getNoArticle());
        return "redirect:/articles";
    }

    @PostMapping("/articles/encheres/{noArticle}")
    public String postEnchere(Model model, @AuthenticationPrincipal CustomUserDetails user, @PathVariable("noArticle") int noArticle, @RequestParam("montantEnchere") int montantEnchere, RedirectAttributes redirectAttr) {
        Optional<Article> articleOptional = articleService.getById(noArticle);
        if(articleOptional.isEmpty()) {
            return "redirect:/articles";
        }

        Article article = articleOptional.get();

        if(article.getUtilisateur().getNoUtilisateur() == user.getNoUtilisateur()) {
            return "redirect:/articles";
        }

        Optional<Utilisateur> utilisateurOptional = utilisateurService.getById(user.getNoUtilisateur());

        if(utilisateurOptional.isEmpty()) {
            return "redirect:/articles";
        }

        Utilisateur utilisateur = utilisateurOptional.get();

        Enchere lastEnchere = articleService.getMeilleurEnchere(article);

        if((lastEnchere != null && lastEnchere.getMontantEnchere() > montantEnchere) ) {
            redirectAttr.addFlashAttribute("errorMinimumEnchere", "Vous enchère doit être supérieur à la meilleur offre");
            return "redirect:/articles/detail/" + article.getNoArticle();
        }

        if(user.getCredit() < montantEnchere) {
            redirectAttr.addFlashAttribute("errorNotEnoughCredit", "Vous n'avez pas assez de crédit");
            return "redirect:/articles/detail/" + article.getNoArticle();
        }

        Enchere enchere = new Enchere();
        enchere.setDateEnchere(LocalDate.now());
        enchere.setMontantEnchere(montantEnchere);
        enchere.setUtilisateur(utilisateur);

        try {
            int newCredit = articleService.enchere(article, enchere);
            customUserDetailsService.updateCredit(newCredit);
            redirectAttr.addFlashAttribute("success", "Votre enchère a bien été prise en compte !");
            return "redirect:/articles/detail/" + article.getNoArticle();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/articles/retrait/{noArticle}")
    public String confirmerRetrait(Model model, @PathVariable("noArticle") int noArticle, @AuthenticationPrincipal CustomUserDetails user) {
        Optional<Article> articleOptional = articleService.getById(noArticle);
        if(articleOptional.isEmpty()) {
            return "redirect:/articles";
        }

        Article article = articleOptional.get();

        if(article.getUtilisateur().getNoUtilisateur() != user.getNoUtilisateur()) {
            return "redirect:/articles";
        }

        EtatVente etatVente = articleService.getEtatVente(article);

        if(etatVente != EtatVente.COMPLETED) {
            return "redirect:/articles";
        }

        articleService.setRetraitEffectue(article);

        return "redirect:/articles/detail/" + article.getNoArticle();
    }


}
