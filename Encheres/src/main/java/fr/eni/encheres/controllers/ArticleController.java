package fr.eni.encheres.controllers;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.services.interf.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<Article> articles = new ArrayList<>();
        Article table = new Article();
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo("Boby66");
        table.setNomArticle("table");
        table.setPrixVente(100);
        table.setNoArticle(1);
        table.setDateFinEncheres(LocalDate.of(2024, 12, 30));
        table.setUtilisateur(utilisateur);
        System.out.println(table);
        articles.add(table);
        model.addAttribute("articles", articles);
        model.addAttribute("body", "pages/articles/listeArticle");
        return "index";
    }

    @GetMapping("/articles/ajouter")
    public String getAjouterArticle(Model model) {
        Article article = new Article();
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setVille("Toulon");
        utilisateur.setCodePostal("83100");
        utilisateur.setRue("50 av sergent gabriel jourdan");
        article.setUtilisateur(utilisateur);
        model.addAttribute("body", "pages/articles/enregistrerArticle");
        model.addAttribute("article", article);
        return "index";
    }
}
