package by.teachmeskills.sneakersshopwebserviceexam.dto.converters;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Order;
import by.teachmeskills.sneakersshopwebserviceexam.dto.OrderDto;
import by.teachmeskills.sneakersshopwebserviceexam.services.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Data
public class OrderConverter {
    private final ProductConverter productConverter;
    private final UserService userService;

    @Autowired
    public OrderConverter(ProductConverter productConverter, UserService userService) {
        this.productConverter = productConverter;
        this.userService = userService;
    }

    public OrderDto toDto(Order order) {
        return Optional.ofNullable(order).map(o -> OrderDto.builder()
                .id(o.getId())
                .price(o.getPrice()).price(o.getPrice())
                .date(o.getDate())
                .userId(o.getUser().getId())
                .productList(Optional.ofNullable(o.getProductList()).map(products -> products.stream().map(productConverter::toDto).toList()).orElse(List.of()))
                .creditCardNumber(o.getCreditCardNumber())
                .shippingType(o.getShippingType())
                .shippingCost(o.getShippingCost())
                .code(o.getCode())
                .address(o.getAddress())
                .customerNotes(o.getCustomerNotes())
                .build()).orElse(null);
    }

    public Order fromDto(OrderDto orderDto) {
        return Optional.ofNullable(orderDto).map(o -> Order.builder()
                .id(o.getId())
                .price(o.getPrice()).price(o.getPrice())
                .date(o.getDate())
                .user(userService.getUserById(o.getUserId()))
                .productList(Optional.ofNullable(o.getProductList()).map(products -> products.stream().map(productConverter::fromDto).toList()).orElse(List.of()))
                .creditCardNumber(o.getCreditCardNumber())
                .shippingType(o.getShippingType())
                .shippingCost(o.getShippingCost())
                .code(o.getCode())
                .address(o.getAddress())
                .customerNotes(o.getCustomerNotes())
                .build()).orElse(null);
    }
}
