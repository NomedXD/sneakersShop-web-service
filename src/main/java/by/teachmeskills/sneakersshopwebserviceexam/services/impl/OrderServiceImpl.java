package by.teachmeskills.sneakersshopwebserviceexam.services.impl;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Cart;
import by.teachmeskills.sneakersshopwebserviceexam.domain.Order;
import by.teachmeskills.sneakersshopwebserviceexam.domain.User;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CartDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.CheckoutRequestResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.CartConverter;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.OrderConverter;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.UserConverter;
import by.teachmeskills.sneakersshopwebserviceexam.exception.NoSuchOrderException;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.OrderRepository;
import by.teachmeskills.sneakersshopwebserviceexam.services.OrderService;
import by.teachmeskills.sneakersshopwebserviceexam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final UserService userService;
    private final OrderConverter orderConverter;
    private final UserConverter userConverter;
    private final CartConverter cartConverter;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, @Lazy OrderConverter orderConverter,
                            @Lazy UserConverter userConverter, @Lazy CartConverter cartConverter) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.orderConverter = orderConverter;
        this.userConverter = userConverter;
        this.cartConverter = cartConverter;
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        return orderConverter.toDto(orderRepository.save(orderConverter.fromDto(orderDto)));
    }

    @Override
    public List<OrderDto> read() {
        return orderRepository.findAll().stream().map(orderConverter::toDto).toList();
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
        return orderConverter.toDto(orderRepository.save(orderConverter.fromDto(orderDto)));
    }

    @Override
    public void delete(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderDto getOrderById(Integer id) {
        return orderConverter.toDto(orderRepository.findOrderById(id).orElseThrow(() -> new NoSuchOrderException("Product not found. Id:", id)));
    }

    @Override
    public List<OrderDto> getUserOrders(Integer userId) {
        return orderRepository.findAllByUserId(userId).stream().map(orderConverter::toDto).toList();
    }

    @Override
    public List<OrderDto> getPaginatedOrders(Integer currentPage, Integer pageSize, Integer userId) {
        Pageable pageable = PageRequest.of((currentPage - 1), pageSize);
        return orderRepository.findAllByUserId(userId, pageable).stream().map(orderConverter::toDto).toList();
    }

    @Override
    public ResponseEntity<CheckoutRequestResponseWrapperDto> applyOrder(OrderDto orderDto, CartDto cartDto, UserDto userDto) {
        Order order = userConverter.getOrderConverter().fromDto(orderDto);
        Cart cart = cartConverter.fromDto(cartDto);
        User user = userConverter.fromDto(userDto);
        preBuildOrder(order, cart);
        user.getOrders().add(orderRepository.save(order));
        user = userConverter.fromDto(userService.update(userConverter.toDto(user)));
        cart.clear();
        return new ResponseEntity<>(CheckoutRequestResponseWrapperDto.builder()
                .order(orderConverter.toDto(order))
                .cart(cartConverter.toDto(cart))
                .user(userConverter.toDto(user)).build(), HttpStatus.CREATED);
    }

    private void preBuildOrder(Order order, Cart cart) {
        order.setDate(LocalDate.now());
        order.setPrice(cart.getTotalPrice());
        String ccNumber = order.getCreditCardNumber();
        order.setCreditCardNumber(ccNumber.substring(0, 4).concat(" **** **** ").concat(ccNumber.substring(12, 16)));
        order.setProductList(cart.getProducts());
    }
}
