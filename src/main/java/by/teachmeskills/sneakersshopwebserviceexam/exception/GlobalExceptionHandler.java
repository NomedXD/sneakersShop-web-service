package by.teachmeskills.sneakersshopwebserviceexam.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NoHandlerFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>("Page not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<String> handleNoSuchUserException(NoSuchUserException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityOperationException.class)
    public ResponseEntity<String> handleEntityOperationException(EntityOperationException exception) {
        if (exception.getException() instanceof ConstraintViolationException) {
            return handleUserAlreadyExistException(new UserAlreadyExistException("User with such email already exist"));
        } else {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handValidationException(ValidationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

