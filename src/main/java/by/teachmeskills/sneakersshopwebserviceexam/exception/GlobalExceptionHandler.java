package by.teachmeskills.sneakersshopwebserviceexam.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
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
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityOperationException.class)
    public ResponseEntity<String> handleEntityOperationException() {
        return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CSVExportException.class)
    public ResponseEntity<String> handleCSVExportException(CSVExportException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CSVImportException.class)
    public ResponseEntity<String> handleCSVImportException(CSVImportException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<String> handleNoSuchProductException(NoSuchProductException exception) {
        return new ResponseEntity<>(exception.getMessage() + exception.getProductId(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchImageException.class)
    public ResponseEntity<String> handleNoSuchImageException(NoSuchImageException exception) {
        return new ResponseEntity<>(exception.getMessage() + exception.getImageId(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchOrderException.class)
    public ResponseEntity<String> handleNoSuchOrderException(NoSuchOrderException exception) {
        return new ResponseEntity<>(exception.getMessage() + exception.getOrderId(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchCategoryException.class)
    public ResponseEntity<String> handleNoSuchCategoryException(NoSuchCategoryException exception) {
        return new ResponseEntity<>(exception.getMessage() + exception.getCategoryId(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        if (exception.getCause() instanceof ConstraintViolationException constraintViolationException) {
            if (constraintViolationException.getConstraintName().equals("users.mail")) {
                return handleUserAlreadyExistException(new UserAlreadyExistException("User with such email already exist"));
            }
        }
        return handleEntityOperationException();
    }
}

