package by.teachmeskills.sneakersshopwebserviceexam.repositories.impl;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Category;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    private final static Logger logger = LoggerFactory.getLogger(CategoryRepositoryImpl.class);

    @Autowired
    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Category create(Category entity) throws EntityOperationException {
        try {
            entityManager.persist(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while creating category. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
        return entity;
    }

    @Override
    public List<Category> read() throws EntityOperationException {
        try {
            return entityManager.createQuery("select c from Category c", Category.class).getResultList();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting all categories. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Category update(Category entity) throws EntityOperationException {
        try {
            return entityManager.merge(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while updating category. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        try {
            Category category = entityManager.find(Category.class, id);
            entityManager.remove(category);
        } catch (PersistenceException e) {
            logger.warn("SQLException while deleting category. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Category getCategoryById(Integer id) throws EntityOperationException {
        try {
            return entityManager.find(Category.class, id);
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting category by it's id. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }
}
