package by.teachmeskills.sneakersshopwebserviceexam.exception;

import by.teachmeskills.sneakersshopwebserviceexam.enums.PagesPathEnum;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException(NoHandlerFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return new ModelAndView(PagesPathEnum.ERROR_PAGE.getPath());
    }

    @ExceptionHandler(NoSuchUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNoSuchUserException(NoSuchUserException exception) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("loginErrorMessage", exception.getMessage());
        return new ModelAndView(PagesPathEnum.LOG_IN_PAGE.getPath(), modelMap);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleUserAlreadyExistException(UserAlreadyExistException exception) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("registrationErrorMessage", exception.getMessage());
        return new ModelAndView(PagesPathEnum.REGISTRATION_PAGE.getPath(), modelMap);
    }

    @ExceptionHandler(EntityOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleEntityOperationException(EntityOperationException exception) {
        if (exception.getException() instanceof ConstraintViolationException) {
            return handleUserAlreadyExistException(new UserAlreadyExistException("User with such email already exist"));
        } else {
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("SQLErrorMessage", exception.getMessage());
            return new ModelAndView(PagesPathEnum.ERROR_PAGE.getPath(), modelMap);
        }
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handValidationException(ValidationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

