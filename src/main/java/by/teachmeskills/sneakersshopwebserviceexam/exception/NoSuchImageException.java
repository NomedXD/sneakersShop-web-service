package by.teachmeskills.sneakersshopwebserviceexam.exception;

import lombok.Getter;

@Getter
public class NoSuchImageException extends IllegalArgumentException{
    private final Integer imageId;

    public NoSuchImageException(String message, Integer imageId) {
        super(message);
        this.imageId = imageId;
    }
}
