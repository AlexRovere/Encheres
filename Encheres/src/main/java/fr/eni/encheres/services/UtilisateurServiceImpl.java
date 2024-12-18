package fr.eni.encheres.services;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interf.UtilisateurRepository;
import fr.eni.encheres.services.interf.UtilisateurService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    // Read
    @Override
    public List<Utilisateur> getAll(){
        return utilisateurRepository.getAll();
    }

    @Override
    public Optional<Utilisateur> getById(int noUtilisateur) {
        return utilisateurRepository.getById(noUtilisateur);
    }

    // Create
    @Override
    public void add(Utilisateur utilisateur) {
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
}
