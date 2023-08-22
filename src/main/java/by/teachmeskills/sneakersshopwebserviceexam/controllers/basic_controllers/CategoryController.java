package by.teachmeskills.sneakersshopwebserviceexam.controllers.basic_controllers;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CategoryDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.ValidationException;
import by.teachmeskills.sneakersshopwebserviceexam.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Tag(name = "category", description = "Category Endpoints")
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Create category",
            description = "Create category",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category was created",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Category object validation error - server error"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Database error - server error"
            )
    })
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto, BindingResult result) {
        if (!result.hasErrors()) {
            return new ResponseEntity<>(categoryService.create(categoryDto), HttpStatus.CREATED);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @Operation(
            summary = "Get all categories",
            description = "Get all categories",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categories were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Database error - server error"
            )
    })
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<>(categoryService.read(), HttpStatus.OK);
    }

    @Operation(
            summary = "Update category",
            description = "Update category",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category was updated",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Category object validation error - server error"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Database error - server error"
            )
    })
    @PutMapping
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, BindingResult result) {
        if (!result.hasErrors()) {
            return new ResponseEntity<>(categoryService.update(categoryDto), HttpStatus.OK);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @Operation(
            summary = "Remove category",
            description = "Remove category by it's id",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category was deleted"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Database error - server error"
            )
    })
    @DeleteMapping("/remove/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryService.delete(id);
    }

    @Operation(
            summary = "Get category",
            description = "Get category by it's id",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category were found",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category were not found"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Database error - server error"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id) {
        return Optional.ofNullable(categoryService.getCategoryById(id))
                .map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
