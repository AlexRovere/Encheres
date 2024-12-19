package fr.eni.encheres.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public class CreateArticleWithUtilisateurAndRetraitAndCategorie {
    private int noArticle;
    private int noCategorie;
    private int noUtilisateur;
    @NotBlank
    @Size(min=3, max= 50)
    private String nomArticle;
    @NotBlank
    @Size(min=3, max = 300)
    private String description;
    @NotNull
    private LocalDate dateDebutEncheres;
    @NotNull
    private LocalDate dateFinEncheres;
    @PositiveOrZero
    private int prixInitial;
    @PositiveOrZero
    private int prixVente;
    private int etatVente;
    @NotNull
    private boolean retraitEffectue;
    private String retraitRue;
    private String retraitVille;
    private String retraitCodePostal;

    public CreateArticleWithUtilisateurAndRetraitAndCategorie() {
    }

    @Override
    public String toString() {
        return "CreateArticleWithUtilisateurAndRetraitAndCategorie{" +
                "noArticle=" + noArticle +
                ", noCategorie=" + noCategorie +
                ", noUtilisateur=" + noUtilisateur +
                ", nomArticle='" + nomArticle + '\'' +
                ", description='" + description + '\'' +
                ", dateDebutEncheres=" + dateDebutEncheres +
                ", dateFinEncheres=" + dateFinEncheres +
                ", prixInitial=" + prixInitial +
                ", prixVente=" + prixVente +
                ", etatVente=" + etatVente +
                ", retraitEffectue=" + retraitEffectue +
                ", retraitRue='" + retraitRue + '\'' +
                ", retraitVille='" + retraitVille + '\'' +
                ", retraitCodePostal='" + retraitCodePostal + '\'' +
                '}';
    }
}
