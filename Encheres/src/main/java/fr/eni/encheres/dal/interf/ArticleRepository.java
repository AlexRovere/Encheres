package fr.eni.encheres.dal.interf;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dto.FilterDto;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article>{
    List<Article> getAllWithFilters(FilterDto filters);
    int enchere(Article article, Enchere enchere);
    void setRetraitEffectue(Article article);
}
