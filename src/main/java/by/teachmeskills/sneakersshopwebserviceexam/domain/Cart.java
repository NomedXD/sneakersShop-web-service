package by.teachmeskills.sneakersshopwebserviceexam.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
public class Cart {
    /*
     *  Annotated for the future admin account in shop
     */
    @NotNull(message = "Field is null validation error")
    @Size(max = 100, message = "Out of validation bounds")
    private final Map<Integer, Product> products;

    @NotNull(message = "Field is null validation error")
    @PositiveOrZero(message = "Field must be positive or zero")
    private float totalPrice;

    // Здесь можно не делать отдельное поле, а сделать в теории обертку над Product, но муторно -_-
    @NotNull(message = "Field is null validation error")
    @Size(max = 100, message = "Out of validation bounds")
    private Map<Integer, Integer> productQuantities;

    public Cart() {
        this.products = new HashMap<>();
        this.totalPrice = 0;
        this.productQuantities = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
        productQuantities.put(product.getId(), 1);
        totalPrice += product.getPrice() * 1;
    }

    public void removeProduct(int productId) {
        Product product = products.get(productId);
        products.remove(productId);
        totalPrice -= product.getPrice() * productQuantities.get(productId);
        productQuantities.remove(productId);
    }

    public static Cart checkCart(Product product, Cart cart){
        if (cart != null) {
            cart.addProduct(product);
        } else {
            cart = new Cart();
            cart.addProduct(product);
        }
        return cart;
    }

    public List<Product> getProducts() {
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
