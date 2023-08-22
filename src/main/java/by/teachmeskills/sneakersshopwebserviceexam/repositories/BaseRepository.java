package by.teachmeskills.sneakersshopwebserviceexam.repositories;

import by.teachmeskills.sneakersshopwebserviceexam.domain.BaseEntity;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;

import java.util.List;

public interface BaseRepository<T extends BaseEntity> {

    T create(T entity) throws EntityOperationException;

    List<T> read() throws EntityOperationException;

    T update(T entity) throws EntityOperationException;

    void delete(Integer id) throws EntityOperationException;
}
