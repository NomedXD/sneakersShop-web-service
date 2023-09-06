package by.teachmeskills.sneakersshopwebserviceexam.controllers;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.UpdateUserRequestWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Tag(name = "account", description = "Account Endpoints")
@RestController
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Update user",
            description = "Update user data by form",
            tags = {"account"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful update",
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Request UpdateUserRequestWrapperDto object validation error - server error"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateAccountData(@Valid @RequestBody UpdateUserRequestWrapperDto requestBody, BindingResult result) {
        if (!result.hasErrors()) {
            return new ResponseEntity<>(userService.updateAccountData(requestBody.getUpdatedUserFields(), requestBody.getUser()), HttpStatus.OK);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @Operation(
            summary = "Change account page",
            description = "Change account page(account orders)",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Account page with user orders loaded",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @GetMapping("/page/{page}")
    public ResponseEntity<List<OrderDto>> changeAccountPage(@Valid @RequestBody UserDto userDto, BindingResult result,
                                                            @PathVariable(name = "page") Integer currentPage,
                                                            @RequestParam(name = "size") Integer pageSize) {
        if (!result.hasErrors()) {
            return userService.getAccount(userDto.getId(), currentPage, pageSize);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @Operation(
            summary = "Change account page size",
            description = "Change account page size and load first page of user orders",
            tags = {"catalog"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Account page with user orders loaded",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @GetMapping("/sized")
    public ResponseEntity<List<OrderDto>> changeAccountPageSize(@Valid @RequestBody UserDto userDto, BindingResult result,
                                                                @RequestParam(name = "size") Integer pageSize) {
        if (!result.hasErrors()) {
            return userService.getAccount(userDto.getId(), 1, pageSize);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }
}
