package by.teachmeskills.sneakersshopwebserviceexam.utils;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.DiscountCodeDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderProductDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ProductDto;
import by.teachmeskills.sneakersshopwebserviceexam.services.DiscountCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Component
public class OrderProductDtoConverter {
    private final DiscountCodeService discountCodeService;

    @Autowired
    public OrderProductDtoConverter(DiscountCodeService discountCodeService) {
        this.discountCodeService = discountCodeService;
    }

    public List<OrderProductDto> convertInto(List<OrderDto> orderDtoList) {
        List<OrderProductDto> orderProductDtoList = new ArrayList<>();
        orderDtoList.forEach(orderDto -> orderDto.getProductList().forEach(productDto -> orderProductDtoList.add(OrderProductDto.builder()
                .productId(productDto.getId())
                .productName(productDto.getName())
                .productImage(productDto.getImageDtoList().get(0))
                .productDescription(productDto.getDescription())
                .categoryId(productDto.getCategoryId())
                .productPrice(productDto.getPrice())
                .orderId(orderDto.getId())
                .totalOrderPrice(orderDto.getPrice())
                .orderDate(orderDto.getDate())
                .userId(orderDto.getUserId())
                .creditCardNumber(orderDto.getCreditCardNumber())
                .shippingType(orderDto.getShippingType())
                .shippingCost(orderDto.getShippingCost())
                .discountCode(Optional.ofNullable(orderDto.getDiscountCode()).map(DiscountCodeDto::getName).orElse(null))
                .address(orderDto.getAddress())
                .customerNotes(orderDto.getCustomerNotes()).build())));
        return orderProductDtoList;
    }

    public List<OrderDto> convertFrom(List<OrderProductDto> orderProductDtoList) {
        return createOrderDtoList(orderProductDtoList);
    }

    private List<OrderDto> createOrderDtoList(List<OrderProductDto> orderProductDtoList) {
        List<OrderDto> orderDtoList = new ArrayList<>();
        Set<Integer> orderIdSet = new HashSet<>();
        orderProductDtoList.forEach(orderProductDto -> {
            if (!orderIdSet.contains(orderProductDto.getOrderId())) {
                orderIdSet.add(orderProductDto.getOrderId());
                orderDtoList.add(OrderDto.builder()
                        .id(orderProductDto.getOrderId())
                        .price(orderProductDto.getTotalOrderPrice())
                        .date(orderProductDto.getOrderDate())
                        .userId(orderProductDto.getUserId())
                        .productList(new ArrayList<>())
                        .creditCardNumber(orderProductDto.getCreditCardNumber())
                        .shippingType(orderProductDto.getShippingType())
                        .shippingCost(orderProductDto.getShippingCost())
                        .discountCode(Optional.ofNullable(orderProductDto.getDiscountCode()).map(discountCodeService::getDiscountCodeByName).orElse(null))
                        .address(orderProductDto.getAddress())
                        .customerNotes(orderProductDto.getCustomerNotes()).build());
            }
        });
        addProductsToOrders(orderProductDtoList, orderDtoList);
        return orderDtoList;
    }

    private void addProductsToOrders(List<OrderProductDto> orderProductDtoList, List<OrderDto> orderDtoList) {
        orderProductDtoList.forEach(orderProductDto -> orderDtoList.forEach(orderDto -> {
            if (Objects.equals(orderProductDto.getOrderId(), orderDto.getId())) {
                orderDto.getProductList().add(ProductDto.builder()
                        .id(orderProductDto.getProductId())
                        .name(orderProductDto.getProductName())
                        .imageDtoList(List.of(orderProductDto.getProductImage()))
                        .description(orderProductDto.getProductDescription())
                        .categoryId(orderProductDto.getCategoryId())
                        .price(orderProductDto.getProductPrice()).build());
            }
        }));
    }
}
