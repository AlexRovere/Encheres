package fr.eni.encheres.controllers.converter;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.services.interf.CategorieService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategorieConverter implements Converter<String, Categorie> {

    private final CategorieService categorieService;

    public CategorieConverter(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @Override
    public Categorie convert(String source) {
        int noCategorie = Integer.parseInt(source);
        Optional<Categorie> optionalCategorie = categorieService.getById(noCategorie);
        return optionalCategorie.orElseThrow(() -> new IllegalArgumentException("Categorie non trouvé pour l'ID: " + noCategorie));    }
}