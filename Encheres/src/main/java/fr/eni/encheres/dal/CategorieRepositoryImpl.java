package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.interf.CategorieRepository;
import fr.eni.encheres.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategorieRepositoryImpl implements CategorieRepository {

    private static final Logger logger = LoggerFactory.getLogger(CategorieRepositoryImpl.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CategorieRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    @Override
    public void add(Categorie categorie) throws DatabaseException {
        String sql = "insert into categories (libelle) values (:libelle)";
        MapSqlParameterSource params = new MapSqlParameterSource("libelle", categorie.getLibelle());
        try{
        namedParameterJdbcTemplate.update(sql, params);
        } catch (DataAccessException e) {
            logger.error("Erreur lors de l'ajout de la catégorie : {}", categorie.getLibelle(), e);
            throw new DatabaseException("Impossible d'ajouter la catégorie : " + categorie.getLibelle(), e);
        }
    }

    @Override
    public List<Categorie> getAll() throws DatabaseException {
        String sql = "select no_categorie as noCategorie, libelle from categories";
        List<Categorie> categories;
        try {
          categories = namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Categorie.class));
        } catch (DataAccessException e) {
            logger.error("Impossible de récupérer la liste des catégories", e);
            throw new DatabaseException("Impossible de récupérer la liste des catégories", e);
        }
        return categories;
    }

    @Override
    public Optional<Categorie> getById(int id) throws DatabaseException {
        String sql = "select no_categorie as \"noCategorie\", libelle from categories where no_categorie = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        Categorie categorie = null;
        try {
            categorie = namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Categorie.class));
        } catch (DataAccessException e) {
            logger.error("Impossible de récupérer la catégorie id : {}", id, e);
            throw new DatabaseException("Impossible de récupérer la catégorie id : " + id, e);
        }
        return Optional.ofNullable(categorie);
    }

    @Override
    public void update(Categorie categorie) throws DatabaseException {
        String sql = "update categories set libelle= :libelle where no_categorie = :noCategorie";
        MapSqlParameterSource params = new MapSqlParameterSource("noCategorie", categorie.getNoCategorie());
        try {
            namedParameterJdbcTemplate.update(sql, params);
        } catch (DataAccessException e) {
            logger.error("Impossible de mettre à jour la catégorie : {}", categorie, e);
            throw new DatabaseException("Impossible de mettre à jour la catégorie : " + categorie, e);
        }
    }

    @Override
    public void delete(int id) throws DatabaseException {
    String sql = "delete from categories where no_categorie = :noCategorie";
        MapSqlParameterSource params = new MapSqlParameterSource("noCategorie", id);
        try {
            namedParameterJdbcTemplate.update(sql, params);
        } catch (DataAccessException e) {
            logger.error("Impossible de supprimer la catégorie id : {}", id, e);
            throw new DatabaseException("Impossible de supprimer la catégorie id : " + id, e);
        }
    }
}
