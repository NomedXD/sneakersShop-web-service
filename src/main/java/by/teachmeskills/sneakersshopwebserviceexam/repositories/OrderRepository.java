package by.teachmeskills.sneakersshopwebserviceexam.repositories;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Order;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order>{
    Order getOrderById(Integer id) throws EntityOperationException;
    List<Order> getUserOrders(Integer userId) throws EntityOperationException;
}
