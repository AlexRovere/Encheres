package fr.eni.encheres.controllers;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.security.CustomUserDetails;
import fr.eni.encheres.services.interf.ArticleService;
import fr.eni.encheres.services.interf.CategorieService;
import fr.eni.encheres.services.interf.UtilisateurService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;
    private final CategorieService categorieService;
    private final UtilisateurService utilisateurService;

    public ArticleController(ArticleService articleService, CategorieService categorieService, UtilisateurService utilisateurService) {
        this.articleService = articleService;
        this.categorieService = categorieService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/articles")
    public String getArticles(Model model) {
        model.addAttribute("categories", categorieService.getAll());
        model.addAttribute("articles", articleService.getAll());
        model.addAttribute("body", "pages/articles/listeArticle");
        return "index";
    }

    @GetMapping("/articles/detail/{noArticle}")
    public String getDetailArticle(Model model, @PathVariable("noArticle") int noArticle) {
        System.out.println(noArticle);
        Optional<Article> articleOptional = articleService.getById(noArticle);
        if(articleOptional.isEmpty()) {
            return "redirect:/articles";
        }
        model.addAttribute("article", articleOptional.get());
        model.addAttribute("body", "pages/articles/detailArticle");
        return "index";
    }


    @GetMapping("/articles/ajouter")
    public String getAjouterArticle(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        Article article = new Article();
        article.setDateDebutEncheres(LocalDate.now());
        article.setDateFinEncheres(LocalDate.now().plusDays(1));

        Optional<Article> optionalArticle = Optional.ofNullable((Article) model.getAttribute("article"));

        article = optionalArticle.orElse(article);

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
    public String postAjouterArticle(Model model, @AuthenticationPrincipal CustomUserDetails user, @Valid @ModelAttribute("article") Article article, BindingResult result,  RedirectAttributes redirectAttr,
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
            redirectAttr.addFlashAttribute( "org.springframework.validation.BindingResult.article", result);
            redirectAttr.addFlashAttribute("article", article);
            return "redirect:/articles/ajouter";
        }
        articleService.add(article);
        return "redirect:/articles";
    }

}
