package by.teachmeskills.sneakersshopwebserviceexam.controllers.basic_controllers;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ProductDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVExportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVImportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.ValidationException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Tag(name = "product", description = "Product Endpoints")
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Create product",
            description = "Create product",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product was created",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Product object validation error - server error"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto, BindingResult result) {
        if (!result.hasErrors()) {
            return new ResponseEntity<>(productService.create(productDto), HttpStatus.CREATED);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @Operation(
            summary = "Get all products",
            description = "Get all products",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(productService.read(), HttpStatus.OK);
    }

    @Operation(
            summary = "Update product",
            description = "Update product",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product was updated",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Product object validation error - server error"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, BindingResult result) {
        if (!result.hasErrors()) {
            return new ResponseEntity<>(productService.update(productDto), HttpStatus.OK);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @Operation(
            summary = "Remove product",
            description = "Remove product by it's id",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product was deleted"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @DeleteMapping("/remove/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
    }

    @Operation(
            summary = "Get product",
            description = "Get product by it's id",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product were found",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product were not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) {
        return Optional.ofNullable(productService.getProductById(id))
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Get products",
            description = "Get products by category id",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @GetMapping("/allByCategory/{id}")
    public ResponseEntity<List<ProductDto>> getCategoryProducts(@PathVariable Integer id) {
        return new ResponseEntity<>(productService.getCategoryProducts(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Get products",
            description = "Get products by order id",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @GetMapping("/allByOrder/{id}")
    public ResponseEntity<List<ProductDto>> getOrderProducts(@PathVariable Integer id) {
        return new ResponseEntity<>(productService.getOrderProducts(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Export products",
            description = "Export category products to csv file by category id",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products were exported"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "CSVExportException was thrown - server error"
            )
    })
    @GetMapping("/export/{categoryId}")
    public ResponseEntity<String> exportCategoryProducts(@PathVariable Integer categoryId) throws CSVExportException {
        return productService.exportCategoryProducts(categoryId);
    }

    @Operation(
            summary = "Import products",
            description = "Import category products from csv file",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products were imported and created in database"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "CSVImportException was thrown - server error"
            )
    })
    @PostMapping("/import")
    public ResponseEntity<List<ProductDto>> importCategoryProducts(@RequestParam("file") MultipartFile file) throws CSVImportException {
        return productService.importCategoryProducts(file);
    }
}
