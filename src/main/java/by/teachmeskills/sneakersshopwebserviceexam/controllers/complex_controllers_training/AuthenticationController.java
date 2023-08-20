package by.teachmeskills.sneakersshopwebserviceexam.controllers.complex_controllers_training;

import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.LoginResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.ValidationException;
import by.teachmeskills.sneakersshopwebserviceexam.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseWrapperDto> logIn(@Valid @RequestBody UserDto userDto, BindingResult result) throws EntityOperationException {
        if (!result.hasErrors()) {
            return userService.logIn(userDto);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }
}
