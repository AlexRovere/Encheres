package fr.eni.encheres.services.interf;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dto.FilterDto;

import java.util.List;

public interface ArticleService extends CrudService<Article> {
    List<Article> getAllWithFilters(FilterDto filters);
}
