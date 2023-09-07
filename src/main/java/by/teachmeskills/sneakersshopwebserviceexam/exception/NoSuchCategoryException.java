package by.teachmeskills.sneakersshopwebserviceexam.exception;

import lombok.Getter;

@Getter
public class NoSuchCategoryException extends IllegalArgumentException {
    private final Integer categoryId;

    public NoSuchCategoryException(String message, Integer categoryId) {
        super(message);
        this.categoryId = categoryId;
    }
}
