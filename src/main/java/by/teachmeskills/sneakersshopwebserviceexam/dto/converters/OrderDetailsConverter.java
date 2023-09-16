package by.teachmeskills.sneakersshopwebserviceexam.dto.converters;

import by.teachmeskills.sneakersshopwebserviceexam.domain.OrderDetails;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDetailsDto;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.OrderRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Data
@Component
public class OrderDetailsConverter {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderDetailsConverter(@Lazy OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDetailsDto toDto(OrderDetails orderDetails) {
        return OrderDetailsDto.builder().id(orderDetails.getId()).orderId(orderDetails.getOrder().getId())
                .productId(orderDetails.getProductId()).productQuantity(orderDetails.getProductQuantity()).build();
    }

    public OrderDetails fromDto(OrderDetailsDto orderDetailsDto) {
        return OrderDetails.builder().id(orderDetailsDto.getId()).order(orderRepository.findOrderById(orderDetailsDto.getOrderId()).get())
                .productId(orderDetailsDto.getProductId()).productQuantity(orderDetailsDto.getProductQuantity()).build();
    }
}
