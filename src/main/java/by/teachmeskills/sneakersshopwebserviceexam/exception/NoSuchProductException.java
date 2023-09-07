package by.teachmeskills.sneakersshopwebserviceexam.exception;

import lombok.Getter;

@Getter
public class NoSuchProductException extends IllegalArgumentException {
    private final Integer productId;

    public NoSuchProductException(String message, Integer productId) {
        super(message);
        this.productId = productId;
    }
}
