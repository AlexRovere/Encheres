package fr.eni.encheres.dal.interf;

import fr.eni.encheres.bo.Utilisateur;

import java.util.Optional;

public interface UtilisateurRepository extends CrudRepository<Utilisateur> {

    Optional<Utilisateur> getByLogin(String login);
}
