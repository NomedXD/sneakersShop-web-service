package by.teachmeskills.sneakersshopwebserviceexam.exception;

public class UserAlreadyExistException extends IllegalArgumentException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
