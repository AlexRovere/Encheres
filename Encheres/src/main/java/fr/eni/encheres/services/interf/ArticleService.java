package fr.eni.encheres.services.interf;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dto.FilterDto;

import java.util.List;
import java.util.Optional;

public interface ArticleService extends CrudService<Article> {
    List<Article> getAllWithFilters(FilterDto filters);
    String getEtatVente(Article article);
    Enchere getMeilleurEnchere(Article article);
    void enchere(Article article, Enchere enchere);
}
