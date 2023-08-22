package by.teachmeskills.sneakersshopwebserviceexam.controllers.complex_controllers_training;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.SearchDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.SearchResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.ValidationException;
import by.teachmeskills.sneakersshopwebserviceexam.services.ProductService;
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

@RestController
@RequestMapping("/search")
public class SearchController {
    private final ProductService productService;

    @Autowired
    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<SearchResponseWrapperDto> getSearchPage() throws EntityOperationException {
        return productService.getPaginatedProducts(null, 1);
    }

    @PostMapping("/{page}") // Заменено на POST так как searchDto должно из сессии браться
    public ResponseEntity<SearchResponseWrapperDto> changeSearchPage(@Valid @RequestBody SearchDto searchDto, BindingResult result, @PathVariable Integer page) throws EntityOperationException {
        if (!result.hasErrors()) {
            return productService.getPaginatedProducts(searchDto, page);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @PostMapping
    public ResponseEntity<SearchResponseWrapperDto> submitSearch(@Valid @RequestBody SearchDto searchDto, BindingResult result) throws EntityOperationException {
        if (!result.hasErrors()) {
            return productService.getPaginatedProducts(searchDto, 1);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }
}
