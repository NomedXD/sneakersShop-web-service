package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode
@SuperBuilder
public class CartDto {

    @NotNull(message = "Field is null validation error")
    @Size(max = 100, message = "Out of validation bounds")
    private final Map<Integer, ProductDto> products;

    @NotNull(message = "Field is null validation error")
    @PositiveOrZero(message = "Field must be positive or zero")
    private float totalPrice;

    @NotNull(message = "Field is null validation error")
    @Size(max = 100, message = "Out of validation bounds")
    private Map<Integer, Integer> productQuantities;

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
