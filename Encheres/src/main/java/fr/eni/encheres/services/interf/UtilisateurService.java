package fr.eni.encheres.services.interf;

import fr.eni.encheres.bo.Utilisateur;

import java.util.Optional;

public interface UtilisateurService extends CrudService<Utilisateur> {
    Optional<Utilisateur> getByLogin(String login);
}
