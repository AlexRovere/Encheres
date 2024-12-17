package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interf.UtilisateurRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UtilisateurRepositoryImpl implements UtilisateurRepository {
    @Override
    public void add(Utilisateur entity) {

    }

    @Override
    public List<Utilisateur> getAll() {
        return List.of();
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
