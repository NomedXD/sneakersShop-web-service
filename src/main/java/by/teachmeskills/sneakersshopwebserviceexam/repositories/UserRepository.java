package by.teachmeskills.sneakersshopwebserviceexam.repositories;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Order;
import by.teachmeskills.sneakersshopwebserviceexam.domain.User;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;

import java.util.List;

public interface UserRepository extends BaseRepository<User>{
    User getUserByCredentials(String mail, String password) throws EntityOperationException;
    List<Order> getUserOrders(Integer id) throws EntityOperationException;
    User getUserById(Integer id) throws EntityOperationException;
}
