package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.interf.ArticleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
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
