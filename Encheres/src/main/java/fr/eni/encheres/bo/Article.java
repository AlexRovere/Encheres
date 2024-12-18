package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Article {
    private int noArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private int prixInitial;
    private int prixVente;
    private int etatVente;
    private boolean retraitEffectue;
    private Retrait retrait;
    private Categorie categorie;
    private Utilisateur utilisateur;
    private List<Enchere> encheres = new ArrayList<>();

    public Article() {
    }

    @Override
    public String toString() {
        return "ArticleVendu{" +
                "noArticle=" + noArticle +
                ", nomArticle='" + nomArticle + '\'' +
                ", description='" + description + '\'' +
                ", dateDebutEncheres=" + dateDebutEncheres +
                ", dateFinEncheres=" + dateFinEncheres +
                ", prixInitial=" + prixInitial +
                ", prixVente=" + prixVente +
                ", etatVente=" + etatVente +
                ", retrait=" + retrait +
                ", categorie=" + categorie +
                ", utilisateur=" + utilisateur +
                ", encheres=" + encheres +
                '}';
    }

    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDate getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDate dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public int getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(int prixInitial) {
        this.prixInitial = prixInitial;
    }

    public int getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }

    public int getEtatVente() {
        return etatVente;
    }

    public void setEtatVente(int etatVente) {
        this.etatVente = etatVente;
    }

    public Retrait getRetrait() {
        return retrait;
    }

    public void setRetrait(Retrait retrait) {
        this.retrait = retrait;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Enchere> getEncheres() {
        return encheres;
    }

    public void setEncheres(List<Enchere> encheres) {
        this.encheres = encheres;
    }

    public boolean isRetraitEffectue() {
        return retraitEffectue;
    }

    public void setRetraitEffectue(boolean retraitEffectue) {
        this.retraitEffectue = retraitEffectue;
    }
}
