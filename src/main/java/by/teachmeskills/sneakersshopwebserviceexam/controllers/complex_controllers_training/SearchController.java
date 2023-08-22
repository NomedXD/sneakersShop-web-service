package by.teachmeskills.sneakersshopwebserviceexam.controllers.complex_controllers_training;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.SearchDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.SearchResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.ValidationException;
import by.teachmeskills.sneakersshopwebserviceexam.services.ProductService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Tag(name = "search", description = "Search Endpoints")
@RestController
@RequestMapping("/search")
public class SearchController {
    private final ProductService productService;

    @Autowired
    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Get search page",
            description = "Get search page initial",
            tags = {"search"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful get",
                    content = @Content(schema = @Schema(implementation = SearchResponseWrapperDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Database error - server error"
            )
    })
    @GetMapping
    public ResponseEntity<SearchResponseWrapperDto> getSearchPage() throws EntityOperationException {
        return productService.getPaginatedProducts(null, 1);
    }

    @Operation(
            summary = "Change search page",
            description = "Change search page with or without Search filter",
            tags = {"search"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful changed page",
                    content = @Content(schema = @Schema(implementation = SearchResponseWrapperDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Search object validation error - server error"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Database error - server error"
            )
    })
    @PostMapping("/{page}") // Заменено на POST так как searchDto должно из сессии браться
    public ResponseEntity<SearchResponseWrapperDto> changeSearchPage(@Valid @RequestBody SearchDto searchDto, BindingResult result, @PathVariable Integer page) throws EntityOperationException {
        if (!result.hasErrors()) {
            return productService.getPaginatedProducts(searchDto, page);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @Operation(
            summary = "Submit search",
            description = "Submit search and apply for first page",
            tags = {"search"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful submit search",
                    content = @Content(schema = @Schema(implementation = SearchResponseWrapperDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Search object validation error - server error"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Database error - server error"
            )
    })
    @PostMapping
    public ResponseEntity<SearchResponseWrapperDto> submitSearch(@Valid @RequestBody SearchDto searchDto, BindingResult result) throws EntityOperationException {
        if (!result.hasErrors()) {
            return productService.getPaginatedProducts(searchDto, 1);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }
}
