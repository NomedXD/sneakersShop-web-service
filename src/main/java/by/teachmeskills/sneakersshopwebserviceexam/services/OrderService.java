package by.teachmeskills.sneakersshopwebserviceexam.services;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CartDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.CheckoutRequestResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    OrderDto create(OrderDto entity) throws EntityOperationException;

    List<OrderDto> read() throws EntityOperationException;

    OrderDto update(OrderDto entity) throws EntityOperationException;

    void delete(Integer id) throws EntityOperationException;

    OrderDto getOrderById(Integer id) throws EntityOperationException;

    List<OrderDto> getUserOrders(Integer userId) throws EntityOperationException;

    ResponseEntity<CheckoutRequestResponseWrapperDto> applyOrder(OrderDto orderDto, CartDto cartDto, UserDto userDto) throws EntityOperationException;
}
