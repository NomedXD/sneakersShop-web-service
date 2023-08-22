package by.teachmeskills.sneakersshopwebserviceexam.repositories.impl;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Order;
import by.teachmeskills.sneakersshopwebserviceexam.domain.User;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.UserRepository;
import by.teachmeskills.sneakersshopwebserviceexam.utils.JPAResultHelper;
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
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    private final static Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User create(User entity) throws EntityOperationException {
        try {
            entityManager.persist(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting users. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later", e);
        }
        return entity;
    }

    @Override
    public List<User> read() throws EntityOperationException {
        try {
            return entityManager.createQuery("select u from User u", User.class).getResultList();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting users. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public User update(User entity) throws EntityOperationException {
        try {
            return entityManager.merge(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting users. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        try {
            User user = entityManager.find(User.class, id);
            entityManager.remove(user);
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting users. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public User getUserByCredentials(String mail, String password) throws EntityOperationException {
        try {
            return (User) JPAResultHelper.getSingleResultOrNull(entityManager.createQuery("select u from User u where u.mail =: mail and u.password =: password", User.class)
                    .setParameter("mail", mail).setParameter("password", password));
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting users. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public List<Order> getUserOrders(Integer id) throws EntityOperationException {
        try {
            return entityManager.find(User.class, id).getOrders();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting orders by user id. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public User getUserById(Integer id) throws EntityOperationException {
        try {
            return entityManager.find(User.class, id);
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting users. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }
}