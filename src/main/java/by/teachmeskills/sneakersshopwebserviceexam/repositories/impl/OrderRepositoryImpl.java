package by.teachmeskills.sneakersshopwebserviceexam.repositories.impl;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Order;
import by.teachmeskills.sneakersshopwebserviceexam.domain.Product;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.OrderRepository;
import com.mysql.cj.x.protobuf.MysqlxCrud;
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
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    private final static Logger logger = LoggerFactory.getLogger(OrderRepositoryImpl.class);

    @Autowired
    public OrderRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Order create(Order entity) throws EntityOperationException {
        try {
            entityManager.persist(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while creating order. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
        return entity;
    }

    @Override
    public List<Order> read() throws EntityOperationException {
        try {
            return entityManager.createQuery("select o from Order o", Order.class).getResultList();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting all orders. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Order update(Order entity) throws EntityOperationException {
        try {
            return entityManager.merge(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while updating order. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        try {
            Order order = entityManager.find(Order.class, id);
            entityManager.remove(order);
        } catch (PersistenceException e) {
            logger.warn("SQLException while deleting order. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Order getOrderById(Integer id) throws EntityOperationException {
        try {
            return entityManager.find(Order.class, id);
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting order by it's id. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public List<Order> getUserOrders(Integer userId) throws EntityOperationException {
        try {
            return entityManager.createQuery("select o from Order o where o.user.id =: userId", Order.class).
                    setParameter("userId", userId).getResultList();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting products by category. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }
}
