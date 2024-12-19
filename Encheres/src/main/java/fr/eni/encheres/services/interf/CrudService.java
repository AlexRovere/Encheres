package fr.eni.encheres.services.interf;

import fr.eni.encheres.exceptions.DatabaseException;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {
    void add(T entity)  throws DatabaseException;

    public List<T> getAll() throws DatabaseException;

    Optional<T> getById(int id) throws DatabaseException;

    void update(T entity) throws DatabaseException;

    void delete(int id) throws DatabaseException;
}
