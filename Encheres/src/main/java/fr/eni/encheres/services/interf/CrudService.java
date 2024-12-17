package fr.eni.encheres.services.interf;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {
    void add(T entity);

    public List<T> getAll();

    Optional<T> getById(int id);

    void update(T entity);

    void delete(int id);
}
