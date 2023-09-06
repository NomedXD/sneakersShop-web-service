package by.teachmeskills.sneakersshopwebserviceexam.controllers;

import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.RegistrationRequestWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.RegistrationResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.ValidationException;
import by.teachmeskills.sneakersshopwebserviceexam.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Tag(name = "registration", description = "Registration Endpoints")
@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Registration user",
            description = "Registration user by form",
            tags = {"registration"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful registered",
                    content = @Content(schema = @Schema(implementation = RegistrationResponseWrapperDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Request RegistrationResponseWrapperDto object validation error - server error"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User with such credentials already exist"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    // Здесь и в других контроллерах можно сделать один wrapperDto, но тогда не все поля будут использоваться => больше размер пакета
    @PostMapping
    public ResponseEntity<RegistrationResponseWrapperDto> register(@Valid @RequestBody RegistrationRequestWrapperDto requestBody, BindingResult result) {
        if (!result.hasErrors()) {
            return userService.register(requestBody.getUser(), requestBody.getRepeatPassword());
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }
}
