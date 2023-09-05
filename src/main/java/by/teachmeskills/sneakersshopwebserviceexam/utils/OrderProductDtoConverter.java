package by.teachmeskills.sneakersshopwebserviceexam.utils;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderProductDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ProductDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class OrderProductDtoConverter {
    public static List<OrderProductDto> convertInto(List<OrderDto> orderDtoList) {
        List<OrderProductDto> orderProductDtoList = new ArrayList<>();
        orderDtoList.forEach(orderDto -> orderDto.getProductList().forEach(productDto -> orderProductDtoList.add(OrderProductDto.builder()
                .productId(productDto.getId())
                .productName(productDto.getName())
                .productImage(productDto.getImage())
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
                .code(orderDto.getCode())
                .address(orderDto.getAddress())
                .customerNotes(orderDto.getCustomerNotes()).build())));
        return orderProductDtoList;
    }

    public static List<OrderDto> convertFrom(List<OrderProductDto> orderProductDtoList) {
        return createOrderDtoList(orderProductDtoList);
    }

    private static List<OrderDto> createOrderDtoList(List<OrderProductDto> orderProductDtoList) {
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
                        .code(orderProductDto.getCode())
                        .address(orderProductDto.getAddress())
                        .customerNotes(orderProductDto.getCustomerNotes()).build());
            }
        });
        addProductsToOrders(orderProductDtoList, orderDtoList);
        return orderDtoList;
    }

    private static void addProductsToOrders(List<OrderProductDto> orderProductDtoList, List<OrderDto> orderDtoList) {
        orderProductDtoList.forEach(orderProductDto -> orderDtoList.forEach(orderDto -> {
            if (Objects.equals(orderProductDto.getOrderId(), orderDto.getId())) {
                orderDto.getProductList().add(ProductDto.builder()
                        .id(orderProductDto.getProductId())
                        .name(orderProductDto.getProductName())
                        .image(orderProductDto.getProductImage())
                        .description(orderProductDto.getProductDescription())
                        .categoryId(orderProductDto.getCategoryId())
                        .price(orderProductDto.getProductPrice()).build());
            }
        }));
    }
}
