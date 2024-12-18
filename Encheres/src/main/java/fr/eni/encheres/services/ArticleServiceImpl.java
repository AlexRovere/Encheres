package fr.eni.encheres.services;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.services.interf.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Override
    public void add(Article entity) {

    }

    @Override
    public List<Article> getAll() {
        return List.of();
    }

    @Override
    public Optional<Article> getById(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Article entity) {

    }

    @Override
    public void delete(int id) {

    }
}
