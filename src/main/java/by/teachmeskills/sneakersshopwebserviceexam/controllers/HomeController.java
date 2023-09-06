package by.teachmeskills.sneakersshopwebserviceexam.controllers;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CategoryDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.ValidationException;
import by.teachmeskills.sneakersshopwebserviceexam.services.CategoryService;
import by.teachmeskills.sneakersshopwebserviceexam.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Tag(name = "catalog", description = "Home Endpoints")
@RestController
@RequestMapping("/catalog")
public class HomeController {
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public HomeController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Get home page",
            description = "Log in and get first home page categories",
            tags = {"catalog"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful logged and get categories",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @PostMapping // Изменено на POST, так как нужно послать данные пользователя как сессию
    public ResponseEntity<List<CategoryDto>> getShopPage(@Valid @RequestBody(required = false) UserDto user, BindingResult result) {
        if (!result.hasErrors()) {
            return userService.checkIfLoggedInUser(user);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @Operation(
            summary = "Change home page",
            description = "Change home page and get categories",
            tags = {"catalog"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Home page with categories loaded",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @GetMapping("/page/{page}")
    public ResponseEntity<List<CategoryDto>> changeHomePage(@PathVariable(name = "page") Integer currentPage,
                                                            @RequestParam(name = "size") Integer pageSize) {
        return categoryService.getPaginatedCategories(currentPage, pageSize);
    }

    @Operation(
            summary = "Change home page size",
            description = "Change home page size and load first page",
            tags = {"catalog"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Home page with categories loaded",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @GetMapping("/sized")
    public ResponseEntity<List<CategoryDto>> changeCategoryPageSize(@RequestParam(name = "size") Integer pageSize) {
        return categoryService.getPaginatedCategories(1, pageSize);
    }
}
