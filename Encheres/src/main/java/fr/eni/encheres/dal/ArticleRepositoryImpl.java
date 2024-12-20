package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interf.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    @Transactional
    public void add(Article article) {
        String sql = "insert into articles (nom_article, description, date_debut_encheres, date_fin_encheres, " +
                "prix_initial, prix_vente, retrait_effectue, no_utilisateur, no_categorie) values (:nomArticle, :description," +
                ":dateDebutEncheres, :dateFinEncheres, :prixInitial, :prixVente, :retraitEffectue, :noUtilisateur, :noCategorie)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nomArticle", article.getNomArticle());
        params.addValue("description", article.getDescription());
        params.addValue("dateDebutEncheres", article.getDateDebutEncheres());
        params.addValue("dateFinEncheres", article.getDateFinEncheres());
        params.addValue("prixInitial", article.getPrixInitial());
        params.addValue("prixVente", article.getPrixVente());
        params.addValue("retraitEffectue", article.isRetraitEffectue());
        params.addValue("noUtilisateur", article.getUtilisateur().getNoUtilisateur());
        params.addValue("noCategorie", article.getCategorie().getNoCategorie());

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            namedParameterJdbcTemplate.update(sql, params, keyHolder);

            int noArticle = (int) keyHolder.getKeys().get("no_article");
            String sqlRetrait = "insert into retraits (no_article, rue, code_postal, ville) values (:noArticle, :rue, :codePostal, :ville)";
            MapSqlParameterSource paramsRetrait = new MapSqlParameterSource();
            paramsRetrait.addValue("noArticle", noArticle);
            paramsRetrait.addValue("rue", article.getRetrait().getRue());
            paramsRetrait.addValue("codePostal", article.getRetrait().getCodePostal());
            paramsRetrait.addValue("ville", article.getRetrait().getVille());
            namedParameterJdbcTemplate.update(sqlRetrait, paramsRetrait);
        } catch (DataAccessException e) {
            logger.error("Impossible de créer l'article : {}", article, e);
        }
    }

    @Override
    public List<Article> getAll() {
        String sql = "select a.no_article, nom_article, description, date_debut_encheres, date_fin_encheres, " +
                "prix_initial, prix_vente, retrait_effectue, a.no_utilisateur, a.no_categorie, u.pseudo, c.libelle,  r.rue, r.code_postal, r.ville from articles a " +
                "left join utilisateurs u on a.no_utilisateur = u.no_utilisateur " +
                "left join retraits r on a.no_article = r.no_article " +
                "left join categories c on a.no_categorie = c.no_categorie";

        List<Article> articles = new ArrayList<>();
        try {
            articles = namedParameterJdbcTemplate.query(sql, new ArticleRowMapper());
        } catch (DataAccessException e) {
            logger.error("Impossible de récupérer la liste des articles", e);
        }

        return articles;
    }

    @Override
    public Optional<Article> getById(int noArticle) {
        String sql = "select a.no_article, nom_article, description, date_debut_encheres, date_fin_encheres, " +
                "prix_initial, prix_vente, retrait_effectue, a.no_utilisateur, a.no_categorie, u.pseudo, c.libelle, r.rue, r.code_postal, r.ville from articles a " +
                "left join utilisateurs u on a.no_utilisateur = u.no_utilisateur " +
                "left join retraits r on a.no_article = r.no_article " +
                "left join categories c on a.no_categorie = c.no_categorie where a.no_article = :no_article";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_article", noArticle);
        Article art = null;
        try {
            art = namedParameterJdbcTemplate.queryForObject(sql, params, new ArticleRowMapper());
        } catch (DataAccessException e) {
            logger.error("Impossible de récupérer l'article id : {}", noArticle, e);
        }
        return Optional.ofNullable(art);
    }

    @Override
    public void update(Article article) {

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

            // Créer le retrait associé
            Retrait retrait = new Retrait();
            retrait.setCodePostal(rs.getString("code_postal"));
            retrait.setRue(rs.getString("rue"));
            retrait.setVille(rs.getString("ville"));
            article.setRetrait(retrait);

            return article;
        }
    }
}
