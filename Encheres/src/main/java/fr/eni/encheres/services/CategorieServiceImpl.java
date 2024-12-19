package fr.eni.encheres.services;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.interf.CategorieRepository;
import fr.eni.encheres.exceptions.DatabaseException;
import fr.eni.encheres.services.interf.CategorieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategorieServiceImpl implements CategorieService {

    private static final Logger logger = LoggerFactory.getLogger(CategorieServiceImpl.class);

    private final CategorieRepository categorieRepository;

    public CategorieServiceImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }
    @Override
    public void add(Categorie entity) {

    }

    @Override
    public List<Categorie> getAll() throws DatabaseException {
        return categorieRepository.getAll();
    }

    @Override
    public Optional<Categorie> getById(int id) {
        Optional<Categorie> categorie = null;
        try {
            categorie = categorieRepository.getById(id);
        } catch (DatabaseException e) {
            System.err.println(e.getMessage() + e.getCause());
        }
        return categorie;
    }

    @Override
    public void update(Categorie entity) {

    }

    @Override
    public void delete(int id) {

    }
}