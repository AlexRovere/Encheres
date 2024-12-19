package fr.eni.encheres.controllers.converter;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.services.interf.UtilisateurService;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UtilisateurConverter implements Converter<String, Utilisateur> {

    private final UtilisateurService utilisateurServiceService;

    public UtilisateurConverter(UtilisateurService utilisateurServiceService) {
        this.utilisateurServiceService = utilisateurServiceService;
    }

    @Override
    public Utilisateur convert(String source) {
        int noUtilisateur = Integer.parseInt(source);
        Optional<Utilisateur> optionalUtilisateur;
        optionalUtilisateur = utilisateurServiceService.getById(noUtilisateur);
        return optionalUtilisateur.orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé pour l'ID: " + noUtilisateur));    }
}