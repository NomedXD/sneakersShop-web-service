package by.teachmeskills.sneakersshopwebserviceexam.services;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CartDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.CheckoutRequestResponseWrapperDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    OrderDto create(OrderDto entity);

    List<OrderDto> read();

    OrderDto update(OrderDto entity);

    void delete(Integer id);

    OrderDto getOrderById(Integer id);

    List<OrderDto> getUserOrders(Integer userId);

    List<OrderDto> getPaginatedOrders(Integer currentPage, Integer pageSize, Integer userId);

    ResponseEntity<CheckoutRequestResponseWrapperDto> applyOrder(OrderDto orderDto, CartDto cartDto, UserDto userDto);
}
