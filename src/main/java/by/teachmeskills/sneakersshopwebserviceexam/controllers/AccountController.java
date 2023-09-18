package by.teachmeskills.sneakersshopwebserviceexam.controllers;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.GetAccountResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVExportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVImportException;
import by.teachmeskills.sneakersshopwebserviceexam.services.AuthService;
import by.teachmeskills.sneakersshopwebserviceexam.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "account", description = "Account Endpoints")
@RestController
@RequestMapping("/account")
public class AccountController {
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public AccountController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Operation(
            summary = "Update user account",
            description = "Update user data by form",
            tags = {"account"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful update",
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateAccountData(@RequestBody UserDto user) {
        return new ResponseEntity<>(userService.updateAccountData(user, authService.getPrincipal().orElse(null)), HttpStatus.OK);
    }

    @Operation(
            summary = "Get account page",
            description = "Get account page and user orders",
            tags = {"account"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Account page with user orders was loaded",
                    content = @Content(schema = @Schema(implementation = GetAccountResponseWrapperDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping
    public ResponseEntity<GetAccountResponseWrapperDto> getAccountPage(@RequestParam(name = "page", required = false) Integer currentPage,
                                                                       @RequestParam(name = "size", required = false) Integer pageSize) {
        return userService.getAccount(authService.getPrincipal().orElse(null), currentPage, pageSize);
    }

    @Operation(
            summary = "Export orders",
            description = "Export user orders to csv file. Products export is separated to another file",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orders were exported"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "CSVExportException was thrown - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportUserOrders() throws CSVExportException {
        return userService.exportUserOrders(authService.getPrincipal().map(UserDto::getId).orElse(null));
    }

    @Operation(
            summary = "Import orders",
            description = "Import user orders from csv file. Products import executes from another file",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orders were imported and created in database",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "CSVImportException was thrown - server error"
            )
    })
    @PostMapping("/import")
    public ResponseEntity<List<OrderDto>> importUserOrders(@RequestParam("file") MultipartFile file) throws CSVImportException {
        return userService.importUserOrders(file);
    }
}
