package fr.eni.encheres.dal.interf;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    void add(T entity);

    List<T> getAll();

    Optional<T> getById(int id);

    void update(T entity);

    void delete(int id);
}
