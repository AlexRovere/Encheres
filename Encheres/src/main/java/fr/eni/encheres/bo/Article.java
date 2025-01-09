package fr.eni.encheres.bo;

import fr.eni.encheres.dto.EtatVente;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Article {
    private int noArticle;

    @NotBlank(message = "Ne doit pas être vide")
    @Size(min=3, max= 50, message="Doit contenir entre 3 et 50 caractères")
    private String nomArticle;

    @NotBlank(message = "Ne doit pas être vide")
    @Size(min=3, max = 300, message="Doit contenir entre 3 et 300 caractères")
    private String description;

    @NotNull(message = "Ne doit pas être vide")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDebutEncheres;

    @NotNull(message = "Ne doit pas être vide")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFinEncheres;

    @PositiveOrZero(message = "Doit être supérieur à 0")
    private int prixInitial;

    private int prixVente;
    private EtatVente etatVente;
    @NotNull
    private boolean retraitEffectue;
    private Retrait retrait;
    private Categorie categorie;
    private Utilisateur utilisateur;
    private List<Enchere> encheres = new ArrayList<>();
    private Image image;
    private LocalDateTime datePaiement;

    public Article() {
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

    public EtatVente getEtatVente() {
        return etatVente;
    }

    public void setEtatVente(EtatVente etatVente) {
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

    @Override
    public String toString() {
        return "Article{" +
                "noArticle=" + noArticle +
                ", nomArticle='" + nomArticle + '\'' +
                ", description='" + description + '\'' +
                ", dateDebutEncheres=" + dateDebutEncheres +
                ", dateFinEncheres=" + dateFinEncheres +
                ", prixInitial=" + prixInitial +
                ", prixVente=" + prixVente +
                ", etatVente=" + etatVente +
                ", retraitEffectue=" + retraitEffectue +
                ", retrait=" + retrait +
                ", categorie=" + categorie +
                ", utilisateur=" + utilisateur +
                ", encheres=" + encheres +
                ", image=" + image +
                '}';
    }

    public boolean isRetraitEffectue() {
        return retraitEffectue;
    }

    public void setRetraitEffectue(boolean retraitEffectue) {
        this.retraitEffectue = retraitEffectue;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }
}
