package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interf.ArticleRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

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
    public List<Article> getAll(){
        String sql = "select no_article, nom_article, description, date_debut_encheres, date_fin_encheres " +
      "prix_initial, prix_vente, retrait_effectue, a.no_utilisateur, a.no_categorie, u.pseudo, c.libelle from articles a " +
       "left join utilisateurs u on a.no_utilisateur = u.no_utilisateur " +
        "left join categories c on a.no_categorie = c.no_categorie";
        List<Article> articles = new ArrayList<>();
        namedParameterJdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
            int noArticle = rs.getInt("no_article");
            String nomArticle = rs.getString("nom_article");
            String description = rs.getString("description");
            LocalDate dateDebutEncheres = rs.getDate("date_debut_encheres").toLocalDate();
            LocalDate dateFinEncheres = rs.getDate("date_fin_encheres").toLocalDate();
            int prixInitial = rs.getInt("prix_initial");
            int prixVente = rs.getInt("prix_vente");
            boolean retraitEffectue = rs.getBoolean("retrait_effectue");
            int noUtilisateur = rs.getInt("no_utilisateur");
            int noCategorie = rs.getInt("no_categorie");
            String pseudo = rs.getString("pseudo");
            String libelle = rs.getString("libelle");

            Article article = new Article();
            article.setNoArticle(noArticle);
            article.setNomArticle(nomArticle);
            article.setDescription(description);
            article.setDateDebutEncheres(dateDebutEncheres);
            article.setDateFinEncheres(dateFinEncheres);
            article.setPrixInitial(prixInitial);
            article.setPrixVente(prixVente);
            article.setRetraitEffectue(retraitEffectue);

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNoUtilisateur(noUtilisateur);
            utilisateur.setPseudo(pseudo);

            article.setUtilisateur(utilisateur);

            Categorie categorie = new Categorie();
            categorie.setNoCategorie(noCategorie);
            categorie.setLibelle(libelle);

            article.setCategorie(categorie);

        articles.add(article);
        return null;
        });
        return articles;
    }

    @Override
    public Optional<Article> getById(int noArticle){
        String sql = "select no_article, nom_article, description, date_debut_encheres, date_fin_encheres " +
                "prix_initial, prix_vente, retrait_effectue, a.no_utilisateur, a.no_categorie, u.pseudo, c.libelle from articles a " +
                "left join utilisateurs u on a.no_utilisateur = u.no_utilisateur " +
                "left join categories c on a.no_categorie = c.no_categorie where no_article = :no_article";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_article", noArticle);
        Article art = namedParameterJdbcTemplate.queryForObject(sql, params, (ResultSet rs, int rowNum) -> {
            String nomArticle = rs.getString("nom_article");
            String description = rs.getString("description");
            LocalDate dateDebutEncheres = rs.getDate("date_debut_encheres").toLocalDate();
            LocalDate dateFinEncheres = rs.getDate("date_fin_encheres").toLocalDate();
            int prixInitial = rs.getInt("prix_initial");
            int prixVente = rs.getInt("prix_vente");
            boolean retraitEffectue = rs.getBoolean("retrait_effectue");
            int noUtilisateur = rs.getInt("no_utilisateur");
            int noCategorie = rs.getInt("no_categorie");
            String pseudo = rs.getString("pseudo");
            String libelle = rs.getString("libelle");

            Article article = new Article();
            article.setNoArticle(noArticle);
            article.setNomArticle(nomArticle);
            article.setDescription(description);
            article.setDateDebutEncheres(dateDebutEncheres);
            article.setDateFinEncheres(dateFinEncheres);
            article.setPrixInitial(prixInitial);
            article.setPrixVente(prixVente);
            article.setRetraitEffectue(retraitEffectue);

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNoUtilisateur(noUtilisateur);
            utilisateur.setPseudo(pseudo);

            article.setUtilisateur(utilisateur);

            Categorie categorie = new Categorie();
            categorie.setNoCategorie(noCategorie);
            categorie.setLibelle(libelle);

            article.setCategorie(categorie);

            return article;

        });
        return Optional.ofNullable(art);
    }

    @Override
    public void update(Article entity) {

    }

    @Override
    public void delete(int id) {

    }
}
