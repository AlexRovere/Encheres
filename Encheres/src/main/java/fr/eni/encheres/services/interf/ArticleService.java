package fr.eni.encheres.services.interf;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dto.EtatVente;
import fr.eni.encheres.dto.FilterDto;

import java.util.List;

public interface ArticleService extends CrudService<Article> {
    List<Article> getAllWithFilters(FilterDto filters);
    EtatVente getEtatVente(Article article);
    Enchere getMeilleurEnchere(Article article);
    int enchere(Article article, Enchere enchere);
}
