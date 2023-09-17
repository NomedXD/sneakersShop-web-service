package by.teachmeskills.sneakersshopwebserviceexam.controllers;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CategoryDto;
import by.teachmeskills.sneakersshopwebserviceexam.enums.EshopConstants;
import by.teachmeskills.sneakersshopwebserviceexam.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name = "catalog", description = "Home Endpoints")
@RestController
@RequestMapping("/catalog")
public class HomeController {
    private final CategoryService categoryService;

    @Autowired
    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Get home page",
            description = "Check log in and get home page with categories",
            tags = {"catalog"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful logged and get categories",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getShopPage(@RequestParam(name = "page", required = false) Integer currentPage,
                                                         @RequestParam(name = "size", required = false) Integer pageSize) {
        if (Optional.ofNullable(currentPage).isPresent() && Optional.ofNullable(pageSize).isPresent()) {
            return new ResponseEntity<>(categoryService.getPaginatedCategories(currentPage, pageSize), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(categoryService.getPaginatedCategories(1, EshopConstants.MIN_PAGE_SIZE), HttpStatus.OK);
        }
    }
}
