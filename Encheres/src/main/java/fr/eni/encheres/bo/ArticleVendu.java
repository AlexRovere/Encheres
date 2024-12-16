package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticleVendu {
    private int noArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private int miseAPrix;
    private int prixVente;
    private int etatVente;
    private Retrait retrait;
    private Categorie categorie;
    private Utilisateur utilisateur;
    private List<Enchere> encheres = new ArrayList<>();
}
