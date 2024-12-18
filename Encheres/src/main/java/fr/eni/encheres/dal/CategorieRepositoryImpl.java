package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.interf.CategorieRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CategorieRepositoryImpl implements CategorieRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CategorieRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    @Override
    public void add(Categorie entity) {

    }

    @Override
    public List<Categorie> getAll() {
        String sql = "select no_categorie as noCategorie, libelle from categories";
        List<Categorie> categories;
        try {
          categories = namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Categorie.class));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    @Override
    public Optional<Categorie> getById(int id) {
        String sql = "select no_categorie as \"noCategorie\", libelle from categories where no_categorie = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        Categorie categorie = namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Categorie.class));
        return Optional.ofNullable(categorie);
    }

    @Override
    public void update(Categorie entity) {

    }

    @Override
    public void delete(int id) {

    }
}
