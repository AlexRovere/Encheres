package fr.eni.encheres.services;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.services.interf.EnchereService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnchereServiceImpl implements EnchereService {
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
