package fr.eni.encheres.services;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.services.interf.UtilisateurService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
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
}
