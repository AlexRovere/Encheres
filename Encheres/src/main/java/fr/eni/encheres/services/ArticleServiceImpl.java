package fr.eni.encheres.services;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.interf.ArticleRepository;
import fr.eni.encheres.dto.FilterDto;
import fr.eni.encheres.services.interf.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    @Override
    public void add(Article article) {
        articleRepository.add(article);
    }

    @Override
    public List<Article> getAll() {
        return articleRepository.getAll();
    }

    @Override
    public Optional<Article> getById(int id) {
        return articleRepository.getById(id);
    }

    @Override
    public void update(Article article) {

    }

    @Override
    public void delete(int id) {
        articleRepository.delete(id);
    }

    public List<Article> getAllWithFilters(FilterDto filters) {
        return articleRepository.getAllWithFilters(filters);
    }

    public String getStatus(Article article) {
        LocalDate start = article.getDateDebutEncheres();
        LocalDate end = article.getDateFinEncheres();
        LocalDate now = LocalDate.now();
//        if(start.)
        return "";
    }
}
