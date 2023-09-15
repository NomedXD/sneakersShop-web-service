package by.teachmeskills.sneakersshopwebserviceexam.controllers;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.JwtRequestDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.JwtResponseDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.RefreshJwtRequestDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.AuthorizationException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.ValidationException;
import by.teachmeskills.sneakersshopwebserviceexam.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Tag(name = "auth", description = "Authentication Endpoints")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthService authService;

    @Autowired
    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Login",
            description = "Give user access and refresh tokens by email and password",
            tags = {"auth"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login is successful\tLogin is unsuccessful",
                    content = {@Content(schema = @Schema(implementation = JwtResponseDto.class)),
                            @Content(schema = @Schema(implementation = String.class))}
            ),
    })
    @PostMapping("/login")
    public JwtResponseDto logIn(@Valid @RequestBody JwtRequestDto authRequest, BindingResult result) throws AuthorizationException {
        if (!result.hasErrors()) {
            return authService.login(authRequest);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @Operation(
            summary = "Access token",
            description = "Get new access token by refresh token")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Access token successfully obtained\tUnauthorized",
                    content = {@Content(schema = @Schema(implementation = JwtResponseDto.class)),
                            @Content(schema = @Schema(implementation = String.class))}
            ),
    })
    @PostMapping("/token")
    public JwtResponseDto getNewAccessToken(@Valid @RequestBody RefreshJwtRequestDto request,
                                            BindingResult bindingResult) throws AuthorizationException {
        if (!bindingResult.hasErrors()) {
            return authService.getAccessToken(request.getRefreshToken());
        } else {
            throw new ValidationException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
    }

    @Operation(
            summary = "Refresh token",
            description = "Get new refresh token")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Refresh token successfully obtained\tUnauthorized",
                    content = {@Content(schema = @Schema(implementation = JwtResponseDto.class)),
                            @Content(schema = @Schema(implementation = String.class))}
            ),
    })
    @PostMapping("/refresh")
    public JwtResponseDto getNewRefreshToken(@Valid @RequestBody RefreshJwtRequestDto request,
                                             BindingResult bindingResult) throws AuthorizationException {
        if (!bindingResult.hasErrors()) {
            return authService.refresh(request.getRefreshToken());
        } else {
            throw new ValidationException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
    }
}
