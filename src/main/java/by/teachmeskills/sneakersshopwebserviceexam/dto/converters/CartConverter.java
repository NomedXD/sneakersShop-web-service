package by.teachmeskills.sneakersshopwebserviceexam.dto.converters;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Cart;
import by.teachmeskills.sneakersshopwebserviceexam.domain.Product;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CartDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ProductDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@Component
public class CartConverter {
    private final ProductConverter productConverter;

    @Autowired
    public CartConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    public CartDto toDto(Cart cart) {
        Map<Integer, ProductDto> productDtoMap = new HashMap<>();
        cart.getProducts().stream().map(productConverter::toDto).toList().forEach(product -> productDtoMap.put(product.getId(), product));
        return Optional.of(cart).map(c -> CartDto.builder()
                .products(productDtoMap)
                .totalPrice(c.getTotalPrice())
                .productQuantities(Optional.ofNullable(c.getProductQuantities()).orElse(new HashMap<>()))
                .build()).orElse(null);
    }

    public Cart fromDto(CartDto cartDto) {
        Map<Integer, Product> productMap = new HashMap<>();
        cartDto.getProducts().stream().map(productConverter::fromDto).toList().forEach(product -> productMap.put(product.getId(), product));
        return Optional.of(cartDto).map(c -> Cart.builder()
                .products(productMap)
                .totalPrice(c.getTotalPrice())
                .productQuantities(Optional.ofNullable(c.getProductQuantities()).orElse(new HashMap<>()))
                .build()).orElse(null);
    }
}
