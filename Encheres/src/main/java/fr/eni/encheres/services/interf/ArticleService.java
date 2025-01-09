package fr.eni.encheres.services.interf;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dto.EtatVente;
import fr.eni.encheres.dto.FilterDto;
import fr.eni.encheres.exceptions.EnchereException;

import java.time.LocalDate;
import java.util.List;

public interface ArticleService extends CrudService<Article> {
    List<Article> getAllWithFilters(FilterDto filters);
    EtatVente getEtatVente(Article article);
    Enchere getMeilleurEnchere(Article article);
    int enchere(Article article, Enchere enchere);
    void setRetraitEffectue(Article article);
    void checkEnchere(Article article, int montantEnchere, int userCredit) throws EnchereException;
    boolean isDateDebutSuperorToDateFin(LocalDate dateDebutEncheres, LocalDate dateFinEncheres);
}
