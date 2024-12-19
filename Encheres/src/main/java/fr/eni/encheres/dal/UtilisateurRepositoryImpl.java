package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interf.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
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
    public void update(Utilisateur entity) {

    }

    // Delete
    @Override
    public void delete(int id) {

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

        return Optional.ofNullable(utilisateur);

    }
}
