package fr.eni.encheres.services;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.interf.CategorieRepository;
import fr.eni.encheres.services.interf.CategorieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieServiceImpl implements CategorieService {
    private final CategorieRepository categorieRepository;

    public CategorieServiceImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }
    @Override
    public void add(Categorie entity) {

    }

    @Override
    public List<Categorie> getAll() {
        return categorieRepository.getAll();
    }

    @Override
    public Optional<Categorie> getById(int id) {
        return categorieRepository.getById(id);
    }

    @Override
    public void update(Categorie entity) {

    }

    @Override
    public void delete(int id) {

    }
}
