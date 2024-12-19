package fr.eni.encheres.services;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interf.UtilisateurRepository;
import fr.eni.encheres.exceptions.DatabaseException;
import fr.eni.encheres.services.interf.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurServiceImpl.class);

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    // Read
    @Override
    public List<Utilisateur> getAll(){
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try {
         utilisateurs = utilisateurRepository.getAll();
        } catch (DatabaseException e) {
            System.err.println(e.getMessage() + e.getCause());
        }
        return utilisateurs;
    }

    @Override
    public Optional<Utilisateur> getById(int noUtilisateur) {
        Optional<Utilisateur> utilisateur = null;
        try {
            utilisateur = utilisateurRepository.getById(noUtilisateur);
        } catch (DatabaseException e) {
            System.err.println(e.getMessage() + e.getCause());
        }
        return utilisateur;
    }

    // Create
    @Override
    public void add(Utilisateur utilisateur) throws DatabaseException {
        utilisateurRepository.add(utilisateur);
    }

    // Update
    @Override
    public void update(Utilisateur entity) {

    }

    // Delete
    @Override
    public void delete(int id) {

    }

    @Override
    public Optional<Utilisateur> getByLogin(String login) {
        return utilisateurRepository.getByLogin(login);
    }
}
