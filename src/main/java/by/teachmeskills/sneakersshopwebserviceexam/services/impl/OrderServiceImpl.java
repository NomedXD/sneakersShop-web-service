package by.teachmeskills.sneakersshopwebserviceexam.services.impl;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Cart;
import by.teachmeskills.sneakersshopwebserviceexam.domain.Order;
import by.teachmeskills.sneakersshopwebserviceexam.domain.OrderDetails;
import by.teachmeskills.sneakersshopwebserviceexam.domain.User;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CartDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.CartConverter;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.OrderConverter;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.UserConverter;
import by.teachmeskills.sneakersshopwebserviceexam.exception.NoSuchOrderException;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.OrderRepository;
import by.teachmeskills.sneakersshopwebserviceexam.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final UserConverter userConverter;
    private final CartConverter cartConverter;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, @Lazy OrderConverter orderConverter,
                            @Lazy UserConverter userConverter, @Lazy CartConverter cartConverter) {
        this.orderRepository = orderRepository;
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
    public ResponseEntity<OrderDto> applyOrder(OrderDto orderDto, CartDto cartDto, UserDto userDto) {
        Order order = userConverter.getOrderConverter().fromDto(orderDto);
        Cart cart = cartConverter.fromDto(cartDto);
        User user = userConverter.fromDto(userDto);
        preBuildOrder(order, cart, user);
        orderRepository.save(order);
        cart.clear();
        return new ResponseEntity<>(orderConverter.toDto(order), HttpStatus.OK);
    }

    private void preBuildOrder(Order order, Cart cart, User user) {
        order.setDate(LocalDate.now());
        order.setPrice(cart.getTotalPrice());
        String ccNumber = order.getCreditCardNumber();
        order.setCreditCardNumber(ccNumber.substring(0, 5).concat(" **** **** ").concat(ccNumber.substring(ccNumber.length()-5)));
        order.setUser(user);
        order.setProductList(cart.getProducts());
        order.setDiscountCode(cart.getAppliedDiscountCode());
        if(Optional.ofNullable(order.getOrderDetails()).isEmpty()){
            order.setOrderDetails(new ArrayList<>());
        }
        cart.getProductQuantities().forEach((productId, productQuantity)-> order.getOrderDetails().add(OrderDetails.builder().order(order).productId(productId).productQuantity(productQuantity).build()));
    }
}
