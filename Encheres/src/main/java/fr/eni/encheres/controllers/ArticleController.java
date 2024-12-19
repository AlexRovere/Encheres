package fr.eni.encheres.controllers;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exceptions.DatabaseException;
import fr.eni.encheres.services.ArticleServiceImpl;
import fr.eni.encheres.services.interf.ArticleService;
import fr.eni.encheres.services.interf.CategorieService;
import fr.eni.encheres.services.interf.UtilisateurService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
        this.utilisateurService =utilisateurService;
    }

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<Article> articles = new ArrayList<>();
        try {
            articles = articleService.getAll();
        } catch (DatabaseException e) {
            logger.debug(e.getMessage() + e.getCause());
        }

        List<Categorie> categories = new ArrayList<>();
        try {
            categories = categorieService.getAll();
        } catch (DatabaseException e) {
            logger.debug(e.getMessage() + e.getCause());
        }
        model.addAttribute("categories", categories);
        model.addAttribute("articles", articles);
        model.addAttribute("body", "pages/articles/listeArticle");
        return "index";
    }

    @GetMapping("/articles/ajouter")
    public String getAjouterArticle(Model model, Principal principal) {
       Optional<Utilisateur> utilisateurOptional = utilisateurService.getByLogin(principal.getName());
       if(utilisateurOptional.isEmpty()) {
           return "redirect:/articles";
       }
        Article article = new Article();
        article.setUtilisateur(utilisateurOptional.get());
        model.addAttribute("body", "pages/articles/enregistrerArticle");
        model.addAttribute("article", article);
        return "index";
    }

    @PostMapping("/articles/ajouter")
    public String postAjouterArticle(Model model, @Valid @ModelAttribute("article") Article article, BindingResult result, RedirectAttributes redirectAttr) {
        System.out.println(article);
        if(result.hasErrors()){
            redirectAttr.addFlashAttribute( "org.springframework.validation.BindingResult.article", result);
            redirectAttr.addFlashAttribute("article", article);
            return "redirect:/article/ajouter/";
        }
        model.addAttribute("body", "pages/articles/listeArticle");
        return "index";
    }

}
