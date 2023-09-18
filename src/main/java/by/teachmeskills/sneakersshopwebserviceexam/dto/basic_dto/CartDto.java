package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Cart;
import by.teachmeskills.sneakersshopwebserviceexam.domain.DiscountCode;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
public class CartDto {

    @NotNull(message = "Product field in cartDto is null")
    @Size(max = 100, message = "Out of bounds cartDto products")
    private Map<Integer, ProductDto> products;

    @NotNull(message = "Total price field in cartDto is null")
    @PositiveOrZero(message = "Total price in categoryDto must be positive or zero")
    private float totalPrice;

    @NotNull(message = "Products quantity field in cartDto is null")
    @Size(max = 100, message = "Out of bounds cartDto products quantity")
    private Map<Integer, Integer> productQuantities;

    @Nullable
    private DiscountCode appliedDiscountCode;

    public void addProduct(ProductDto productDto) {
        products.put(productDto.getId(), productDto);
        productQuantities.put(productDto.getId(), 1);
        totalPrice += productDto.getPrice() * 1;
    }

    public void removeProduct(Integer productId) {
        ProductDto productDto = products.get(productId);
        products.remove(productId);
        totalPrice -= productDto.getPrice() * productQuantities.get(productId);
        productQuantities.remove(productId);
    }

    public static Cart applyDiscountCode(DiscountCode discountCode, Cart cart) {
        if (cart != null) {
            cart.setAppliedDiscountCode(discountCode);
        } else {
            cart = new Cart();
            cart.setAppliedDiscountCode(discountCode);
        }
        return cart;
    }

    public List<ProductDto> getProducts() {
        return new ArrayList<>(products.values());
    }

    public int getTotalSize() {
        return products.size();
    }

    public void clear() {
        products.clear();
        productQuantities.clear();
    }
}
