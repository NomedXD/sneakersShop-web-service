package by.teachmeskills.sneakersshopwebserviceexam.controllers;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.SearchDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.SearchResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.enums.EshopConstants;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

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
            description = "Get search page and it's ptoducts",
            tags = {"search"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful get search page",
                    content = @Content(schema = @Schema(implementation = SearchResponseWrapperDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @GetMapping
    public ResponseEntity<SearchResponseWrapperDto> getSearchPage(@Valid @RequestBody SearchDto searchDto, BindingResult result,
                                                                  @RequestParam(name = "page") Integer currentPage,
                                                                  @RequestParam(name = "size") Integer pageSize) {
        if (!result.hasErrors()) {
            if (Optional.ofNullable(currentPage).isPresent() && Optional.ofNullable(pageSize).isPresent()) {
                return productService.getSearchedPaginatedProducts(searchDto, currentPage, pageSize);
            } else {
                return productService.getSearchedPaginatedProducts(searchDto, 1, EshopConstants.MIN_PAGE_SIZE);
            }
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
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @PostMapping
    public ResponseEntity<SearchResponseWrapperDto> submitSearch(@Valid @RequestBody SearchDto searchDto, BindingResult result,
                                                                 @RequestParam(name = "size") Integer pageSize) {
        if (!result.hasErrors()) {
            return productService.getSearchedPaginatedProducts(searchDto, 1, pageSize);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }
}
