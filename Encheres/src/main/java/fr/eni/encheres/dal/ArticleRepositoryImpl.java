package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interf.ArticleRepository;
import fr.eni.encheres.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    private static final Logger logger = LoggerFactory.getLogger(ArticleRepositoryImpl.class);

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ArticleRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void add(Article entity) {

    }

    @Override
    public List<Article> getAll() throws DatabaseException {
        String sql = "select no_article, nom_article, description, date_debut_encheres, date_fin_encheres, " +
      "prix_initial, prix_vente, retrait_effectue, a.no_utilisateur, a.no_categorie, u.pseudo, c.libelle from articles a " +
       "left join utilisateurs u on a.no_utilisateur = u.no_utilisateur " +
        "left join categories c on a.no_categorie = c.no_categorie";

        List<Article> articles;
        try {
            articles =  namedParameterJdbcTemplate.query(sql, new ArticleRowMapper());
        } catch (DataAccessException e) {
            logger.error("Impossible de récupérer la liste des articles", e);
            throw new DatabaseException("Impossible de récupérer la liste des articles", e);
        }

        return articles;
    }

    @Override
    public Optional<Article> getById(int noArticle) throws DatabaseException{
        String sql = "select no_article, nom_article, description, date_debut_encheres, date_fin_encheres, " +
                "prix_initial, prix_vente, retrait_effectue, a.no_utilisateur, a.no_categorie, u.pseudo, c.libelle from articles a " +
                "left join utilisateurs u on a.no_utilisateur = u.no_utilisateur " +
                "left join categories c on a.no_categorie = c.no_categorie where no_article = :no_article";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_article", noArticle);
        Article art = null;
        try {
            art = namedParameterJdbcTemplate.queryForObject(sql, params, new ArticleRowMapper());
        } catch (DataAccessException e) {
            logger.error("Impossible de récupérer l'article id : {}", noArticle , e);
            throw new DatabaseException("Impossible de récupérer l'artcile id : " + noArticle, e);
        }
        return Optional.ofNullable(art);
    }

    @Override
    public void update(Article entity) {

    }

    @Override
    public void delete(int id) {

    }

    private static class ArticleRowMapper implements RowMapper<Article> {

        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            // Créer l'article
            Article article = new Article();
            article.setNoArticle(rs.getInt("no_article"));
            article.setNomArticle(rs.getString("nom_article"));
            article.setDescription(rs.getString("description"));
            article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
            article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
            article.setPrixInitial(rs.getInt("prix_initial"));
            article.setPrixVente(rs.getInt("prix_vente"));
            article.setRetraitEffectue(rs.getBoolean("retrait_effectue"));

            // Créer l'utilisateur associé
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
            utilisateur.setPseudo(rs.getString("pseudo"));
            article.setUtilisateur(utilisateur);

            // Créer la catégorie associée
            Categorie categorie = new Categorie();
            categorie.setNoCategorie(rs.getInt("no_categorie"));
            categorie.setLibelle(rs.getString("libelle"));
            article.setCategorie(categorie);

            return article;
        }
    }
}
