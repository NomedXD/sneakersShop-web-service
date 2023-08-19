package by.teachmeskills.sneakersshopwebserviceexam.controllers.basic_controllers;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Order;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ProductDto;
import by.teachmeskills.sneakersshopwebserviceexam.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        return new ResponseEntity<>(orderService.create(orderDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return new ResponseEntity<>(orderService.read(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<OrderDto> updateProduct(@RequestBody OrderDto orderDto) {
        return new ResponseEntity<>(orderService.update(orderDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        orderService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Integer id) {
        return Optional.ofNullable(orderService.getOrderById(id))
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/allByUser/{id}")
    public ResponseEntity<List<OrderDto>> getUserOrders(@PathVariable Integer id) {
        return new ResponseEntity<>(orderService.getUserOrders(id), HttpStatus.OK);
    }
}
