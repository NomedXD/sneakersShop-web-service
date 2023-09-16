package by.teachmeskills.sneakersshopwebserviceexam.dto.converters;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Order;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
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
    private final DiscountCodeConverter discountCodeConverter;
    private final OrderDetailsConverter orderDetailsConverter;
    private final UserService userService;

    @Autowired
    public OrderConverter(ProductConverter productConverter, DiscountCodeConverter discountCodeConverter,
                          OrderDetailsConverter orderDetailsConverter, UserService userService) {
        this.productConverter = productConverter;
        this.discountCodeConverter = discountCodeConverter;
        this.orderDetailsConverter = orderDetailsConverter;
        this.userService = userService;
    }

    public OrderDto toDto(Order order) {
         OrderDto orderDto =  Optional.ofNullable(order).map(o -> OrderDto.builder()
                .id(o.getId())
                .price(o.getPrice()).price(o.getPrice())
                .date(o.getDate())
                .userId(o.getUser().getId())
                .productList(Optional.ofNullable(o.getProductList()).map(products -> products.stream().map(productConverter::toDto).toList()).orElse(List.of()))
                .creditCardNumber(o.getCreditCardNumber())
                .shippingType(o.getShippingType())
                .shippingCost(o.getShippingCost())
                .address(o.getAddress())
                .customerNotes(o.getCustomerNotes())
                 .orderDetailsDtoList(Optional.ofNullable(order.getOrderDetails()).map(orderDetails -> orderDetails.stream().map(orderDetailsConverter::toDto).toList()).orElse(null))
                .build()).orElse(null);
        assert order != null;
        if (Optional.ofNullable(order.getDiscountCode()).isPresent()) {
             orderDto.setDiscountCode(discountCodeConverter.toDto(order.getDiscountCode()));
        }
        return orderDto;
    }

    public Order fromDto(OrderDto orderDto) {
        Order order = Optional.ofNullable(orderDto).map(o -> Order.builder()
                .id(o.getId())
                .price(o.getPrice()).price(o.getPrice())
                .date(o.getDate())
                .productList(Optional.ofNullable(o.getProductList()).map(products -> products.stream().map(productConverter::fromDto).toList()).orElse(List.of()))
                .creditCardNumber(o.getCreditCardNumber())
                .shippingType(o.getShippingType())
                .shippingCost(o.getShippingCost())
                .address(o.getAddress())
                .customerNotes(o.getCustomerNotes())
                .orderDetails(Optional.ofNullable(orderDto.getOrderDetailsDtoList()).map(orderDetailsDtos -> orderDetailsDtos.stream().map(orderDetailsConverter::fromDto).toList()).orElse(null))
                .build()).orElse(null);
        assert orderDto != null;
        if (Optional.ofNullable(orderDto.getDiscountCode()).isPresent()) {
            order.setDiscountCode(discountCodeConverter.fromDto(orderDto.getDiscountCode()));
        }
        return order;
    }
}
