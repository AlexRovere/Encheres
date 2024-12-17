package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.interf.EnchereRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EnchereRepositoryImpl implements EnchereRepository {
    @Override
    public void add(Enchere entity) {

    }

    @Override
    public List<Enchere> getAll() {
        return List.of();
    }

    @Override
    public Optional<Enchere> getById(int id) {
        return Optional.empty();
    }

    @Override
    public void update(Enchere entity) {

    }

    @Override
    public void delete(int id) {

    }
}
