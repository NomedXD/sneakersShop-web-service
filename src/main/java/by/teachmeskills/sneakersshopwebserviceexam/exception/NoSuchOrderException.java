package by.teachmeskills.sneakersshopwebserviceexam.exception;

import lombok.Getter;

@Getter
public class NoSuchOrderException extends IllegalArgumentException {
    private final Integer orderId;

    public NoSuchOrderException(String message, Integer orderId) {
        super(message);
        this.orderId = orderId;
    }
}
