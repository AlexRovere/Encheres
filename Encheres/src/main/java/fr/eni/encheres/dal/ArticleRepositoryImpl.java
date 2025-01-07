package fr.eni.encheres.dal;

import fr.eni.encheres.bo.*;
import fr.eni.encheres.dal.interf.ArticleRepository;
import fr.eni.encheres.dto.FilterDto;
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

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Transactional
    @Override
    public Optional<Article> getById(int noArticle) {
        String sql = "select a.no_article, nom_article, description, date_debut_encheres, date_fin_encheres, " +
                "prix_initial, prix_vente, retrait_effectue, a.no_utilisateur, a.no_categorie, u.pseudo, c.libelle, r.rue, r.code_postal, r.ville from articles a " +
                "left join utilisateurs u on a.no_utilisateur = u.no_utilisateur " +
                "left join retraits r on a.no_article = r.no_article " +
                "left join categories c on a.no_categorie = c.no_categorie " +
                "where a.no_article = :noArticle";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("noArticle", noArticle);
        String sqlEnchere = "select e.no_utilisateur, no_article, date_enchere, montant_enchere, u.pseudo, u.credit from encheres e left join utilisateurs u on u.no_utilisateur = e.no_utilisateur where no_article = :noArticle";

        Article art = null;
        try {
            art = namedParameterJdbcTemplate.queryForObject(sql, params, new ArticleRowMapper());
            List<Enchere> encheres = namedParameterJdbcTemplate.query(sqlEnchere, params, new EnchereRowMapper());
            art.setEncheres(encheres);
        } catch (DataAccessException e) {
            logger.error("Impossible de récupérer l'article id : {}", noArticle, e);
        }
        return Optional.ofNullable(art);
    }

    @Override
    @Transactional
    public void update(Article article) {
        String sqlRetrait = "update retraits set rue = :rue, ville = :ville, code_postal = :codePostal where no_article = :noArticle";
        MapSqlParameterSource paramsRetrait = new MapSqlParameterSource();
        paramsRetrait.addValue("noArticle", article.getNoArticle());
        paramsRetrait.addValue("rue", article.getRetrait().getRue());
        paramsRetrait.addValue("ville", article.getRetrait().getVille());
        paramsRetrait.addValue("codePostal", article.getRetrait().getCodePostal());

        String sqlArticle = "update articles SET nom_article = :nomArticle, description = :description, date_debut_encheres = :dateDebutEncheres, date_fin_encheres = :dateFinEncheres, " +
                "prix_initial = :prixInitial, no_categorie = :noCategorie where no_article = :noArticle";
        MapSqlParameterSource paramsArticle = new MapSqlParameterSource();
        paramsArticle.addValue("noArticle", article.getNoArticle());
        paramsArticle.addValue("nomArticle", article.getNomArticle());
        paramsArticle.addValue("description", article.getDescription());
        paramsArticle.addValue("dateDebutEncheres", article.getDateDebutEncheres());
        paramsArticle.addValue("dateFinEncheres", article.getDateFinEncheres());
        paramsArticle.addValue("prixInitial", article.getPrixInitial());
        paramsArticle.addValue("noCategorie", article.getCategorie().getNoCategorie());

        try {
            namedParameterJdbcTemplate.update(sqlRetrait, paramsRetrait);
            namedParameterJdbcTemplate.update(sqlArticle, paramsArticle);

        } catch (DataAccessException e) {
            logger.error("Impossible de modifier l'article id : {}", article.getNoArticle(), e);
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        String sqlRetrait = "delete from retraits where no_article = :noArticle";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("noArticle", id);

        String sqlArticle = "delete from articles where no_article = :noArticle";
        try {
            namedParameterJdbcTemplate.update(sqlRetrait, params);
            namedParameterJdbcTemplate.update(sqlArticle, params);
        } catch (DataAccessException e) {
            logger.error("Impossible de supprimer l'article id : {}", id, e);
        }
    }

    @Override
    public List<Article> getAllWithFilters(FilterDto filters) {
        StringBuilder sql = new StringBuilder();
        sql.append("select a.no_article, nom_article, description, date_debut_encheres, date_fin_encheres, " +
                "prix_initial, prix_vente, retrait_effectue, a.no_utilisateur, a.no_categorie, u.pseudo, c.libelle,  r.rue, r.code_postal, r.ville from articles a " +
                "left join utilisateurs u on a.no_utilisateur = u.no_utilisateur " +
                "left join retraits r on a.no_article = r.no_article " +
                "left join categories c on a.no_categorie = c.no_categorie ");

        boolean where = false;

        if (filters.getNomArticle() != null && !filters.getNomArticle().isBlank()) {
            if (!where) {
                sql.append("where ");
                where = true;
            }
            sql.append("lower(nom_article) LIKE '%").append(filters.getNomArticle().toLowerCase()).append("%' ");
        }

        if (filters.getNoCategorie() != null && filters.getNoCategorie() > 0) {
            if (!where) {
                sql.append("where ");
                where = true;
            } else {
                sql.append("and ");
            }
            sql.append("c.no_categorie = ").append(filters.getNoCategorie()).append(" ");
        }


        List<Article> articles = new ArrayList<>();
        try {
            articles = namedParameterJdbcTemplate.query(sql.toString(), new ArticleRowMapper());
        } catch (DataAccessException e) {
            logger.error("Impossible de récupérer la liste des articles", e);
        }
        return articles;
    }

    @Transactional
    @Override
    public int enchere(Article article, Enchere enchere) {
        String sql = "insert into encheres (no_utilisateur, no_article, date_enchere, montant_enchere) values (:noUtilisateur, :noArticle, :dateEnchere, :montantEnchere)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("noUtilisateur", enchere.getUtilisateur().getNoUtilisateur());
        params.addValue("noArticle", article.getNoArticle());
        params.addValue("dateEnchere", enchere.getDateEnchere());
        params.addValue("montantEnchere", enchere.getMontantEnchere());

        try {
            namedParameterJdbcTemplate.update(sql, params);
            return removeCredit(enchere);
        } catch (DataAccessException e) {
            logger.error("Impossible de faire l'enchère", e);
        }
        return enchere.getUtilisateur().getCredit();
    }

    public int removeCredit(Enchere enchere) {
        int credit = enchere.getUtilisateur().getCredit() - enchere.getMontantEnchere();
        System.out.println(enchere);
        String sql = "update utilisateurs set credit = :credit where no_utilisateur = :noUtilisateur";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("noUtilisateur", enchere.getUtilisateur().getNoUtilisateur());
        params.addValue("credit", credit);

        namedParameterJdbcTemplate.update(sql, params);

        return credit;
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

    private static class EnchereRowMapper implements RowMapper<Enchere> {

        @Override
        public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
            Enchere enchere = new Enchere();
            enchere.setMontantEnchere(rs.getInt("montant_enchere"));
            enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
            utilisateur.setPseudo(rs.getString("pseudo"));
            utilisateur.setCredit(rs.getInt("credit"));

            enchere.setUtilisateur(utilisateur);
            return enchere;
        }
    }

}
