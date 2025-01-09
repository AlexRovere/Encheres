package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interf.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UtilisateurRepositoryImpl implements UtilisateurRepository {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurRepositoryImpl.class);

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UtilisateurRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    // Read
    @Override
    public List<Utilisateur> getAll() {
        String sql = "SELECT no_utilisateur AS \"noUtilisateur\", " +
                     "pseudo, nom, prenom, email, telephone, rue, " +
                     "code_postal AS \"codePostal\", ville, mot_de_passe AS \"motDePasse\", credit " +
                     "FROM utilisateurs";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    public Optional<Utilisateur> getById(int noUtilisateur) {
        String sql = "SELECT * FROM utilisateurs WHERE no_utilisateur = :noUtilisateur";
        Map<String, Object> params = new HashMap<>();
        params.put("noUtilisateur", noUtilisateur);
        try {
            Utilisateur utilisateur = namedParameterJdbcTemplate.queryForObject(sql, params,
                    new BeanPropertyRowMapper<>(Utilisateur.class));
            ;           return Optional.of(utilisateur);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    // Create
    @Override
    public void add(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateurs (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe ) " +
                "VALUES (:pseudo, :nom, :prenom, :email, :telephone, :rue, :codePostal, :ville, :motDePasse)";
        namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(utilisateur));
    }

    // Update
    @Override
    public void update(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateurs SET pseudo = :pseudo, nom = :nom, prenom = :prenom, email = :email, telephone = :telephone, rue = :rue, code_postal = :codePostal, ville = :ville" +
                " WHERE no_utilisateur = :noUtilisateur " ;
        try {
            namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(utilisateur));
        } catch (DataAccessException e) {
            logger.error("Erreur lors de la mis à jour de l'utilisateur", e);
        }
    }

    // Delete
    @Override
    public void delete(int noUtilisateur) {
        String sql = "DELETE FROM utilisateurs WHERE no_utilisateur = :noUtilisateur";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("noUtilisateur", noUtilisateur);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<Utilisateur> getByLogin(String login) {
        String sql = "select no_utilisateur AS \"noUtilisateur\", \n" +
                "                     pseudo, nom, prenom, email, telephone, rue,\n" +
                "                     code_postal AS \"codePostal\", ville, mot_de_passe AS \"motDePasse\", credit, administrateur\n" +
                "                     from utilisateurs where email = :login";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", login);
        Utilisateur utilisateur = namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Utilisateur.class));
        if(utilisateur != null) {
            checkVente(utilisateur);
        }
        return Optional.ofNullable(utilisateur);
    }

    public void checkVente(Utilisateur utilisateur) {
        String sql = "select a.no_article, e.montant_enchere from articles a left join encheres e on e.no_article = a.no_article " +
        "where a.no_utilisateur = :noUtilisateur and a.date_paiement is null and a.date_fin_encheres < NOW() and e.date_remboursement is null";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("noUtilisateur", utilisateur.getNoUtilisateur());

        List<Article> articles = namedParameterJdbcTemplate.query(sql, params, (ResultSet rs, int rowNum) -> {
            Article article = new Article();
            article.setNoArticle(rs.getInt("no_article"));
            article.setPrixVente(rs.getInt("montant_enchere"));
            article.setDatePaiement(LocalDateTime.now());

            return article;
        });
        
        Integer creditToAdd = 0;

        for(Article article : articles) {
            String sqlArticle = "update articles set prix_vente = :prixVente, date_paiement = :datePaiement where no_article = :noArticle";
            MapSqlParameterSource paramsArticle = new MapSqlParameterSource();
            paramsArticle.addValue("prixVente", article.getPrixVente());
            paramsArticle.addValue("datePaiement", article.getDatePaiement());
            paramsArticle.addValue("noArticle", article.getNoArticle());

            creditToAdd += article.getPrixVente();

            namedParameterJdbcTemplate.update(sqlArticle, paramsArticle);
        }
        logger.info("CREDIT TO ADD " + creditToAdd);
        if(creditToAdd > 0) {
            try {
                utilisateur.setCredit(utilisateur.getCredit() + creditToAdd);
                setCreditInDb(utilisateur);
            } catch(Exception e) {
                logger.error("Erreur lors de l'ajout de crédit", e);
            }
        }
    }

    public void setCreditInDb(Utilisateur utilisateur) {
        String sql = "update utilisateurs set credit = :credit where no_utilisateur = :noUtilisateur";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("credit", utilisateur.getCredit());
        params.addValue("noUtilisateur", utilisateur.getNoUtilisateur());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void updatePassword(Utilisateur utilisateur) {
        String sql = "update utilisateurs set mot_de_passe = :motDePasse where no_utilisateur = :noUtilisateur";
        namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(utilisateur));
    }
}
