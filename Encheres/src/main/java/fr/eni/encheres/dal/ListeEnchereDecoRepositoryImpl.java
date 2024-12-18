package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interf.UtilisateurRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository
public class ListeEnchereDecoRepositoryImpl implements UtilisateurRepository {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ListeEnchereDecoRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    @Override
    public List<Utilisateur> getAll(){
        String sql = "SELECT no_article AS \"noArticle\", nom_articles AS \"nomArticle\", descritpion," +
                "date_debut_encheres AS \"dateDebutEnchere\", date_fin_encheres AS \"dateFinEncheres\"," +
                "prix_initial AS \"prixInitial\", prix_vente AS \"prixVente\", retraite_effectue AS \"retraitEffectue\"," +
                "FROM article";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Article.class));
    }


    @Override
    public void add(Article entity){

    }

    @Override
    public Optional<Article> getById(int noArticle){
        String sql = "select pseudo from utilisateur where = :no_article" + "select categorie, utilisateur from article where = :no_article ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_article", noArticle);
        Article article = namedParameterJdbcTemplate.queryForObject(sql, params, ((ResultSet rs, int rowNum) -> {
            String pseudo = rs.getString("pseudo");
            String categorie = rs.getString("categorie");
            int noCategorie = rs.getInt(categorie);

        }
        ));
    }


}


