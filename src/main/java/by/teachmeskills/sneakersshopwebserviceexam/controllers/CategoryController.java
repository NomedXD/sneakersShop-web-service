package by.teachmeskills.sneakersshopwebserviceexam.controllers;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CategoryDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ProductDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVExportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVImportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.ValidationException;
import by.teachmeskills.sneakersshopwebserviceexam.services.CategoryService;
import by.teachmeskills.sneakersshopwebserviceexam.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Tag(name = "category", description = "Category Endpoints")
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
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
                    responseCode = "500",
                    description = "Database error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.create(categoryDto), HttpStatus.CREATED);
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
                    responseCode = "500",
                    description = "Database error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
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
                    description = "Category object validation error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
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
                    responseCode = "500",
                    description = "Database error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
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
                    description = "Category were not found",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/cid/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id) {
        return Optional.ofNullable(categoryService.getCategoryById(id))
                .map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Get category page",
            description = "Get category page and it's products",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category page with products was loaded",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/{categoryId}")
    public ResponseEntity<List<ProductDto>> getCategoryPage(@PathVariable(name = "categoryId") Integer categoryId,
                                                            @RequestParam(name = "page", required = false) Integer currentPage,
                                                            @RequestParam(name = "size", required = false) Integer pageSize) {
        return productService.getPaginatedProductsByCategoryId(categoryId, currentPage, pageSize);
    }

    @Operation(
            summary = "Export categories",
            description = "Export all categories to csv file",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categories were exported"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "CSVExportException was thrown - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportCategories() throws CSVExportException {
        return categoryService.exportCategories();
    }

    @Operation(
            summary = "Import categories",
            description = "Import categories from csv file",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categories were imported and created in database",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "CSVImportException was thrown - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping("/import")
    public ResponseEntity<List<CategoryDto>> importCategories(@RequestParam("file") MultipartFile file) throws CSVImportException {
        return categoryService.importCategories(file);
    }
}
