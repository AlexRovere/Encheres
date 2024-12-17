package fr.eni.encheres.dal;

import com.fasterxml.jackson.databind.util.Named;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interf.UtilisateurRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UtilisateurRepositoryImpl implements UtilisateurRepository {

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
    public void add(Utilisateur entity) {

    }

    @Override
    public Optional<Utilisateur> getById(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Utilisateur entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Optional<Utilisateur> getByLogin(String login) {
        return Optional.empty();
    }
}
