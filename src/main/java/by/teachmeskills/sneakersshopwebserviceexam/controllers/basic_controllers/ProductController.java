package by.teachmeskills.sneakersshopwebserviceexam.controllers.basic_controllers;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ProductDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.ValidationException;
import by.teachmeskills.sneakersshopwebserviceexam.services.ProductService;
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

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto, BindingResult result) {
        if (!result.hasErrors()) {
            return new ResponseEntity<>(productService.create(productDto), HttpStatus.CREATED);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(productService.read(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, BindingResult result) {
        if (!result.hasErrors()) {
            return new ResponseEntity<>(productService.update(productDto), HttpStatus.OK);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @DeleteMapping("/remove/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) {
        return Optional.ofNullable(productService.getProductById(id))
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/allByCategory/{id}")
    public ResponseEntity<List<ProductDto>> getCategoryProducts(@PathVariable Integer id) {
        return new ResponseEntity<>(productService.getCategoryProducts(id), HttpStatus.OK);
    }

    @GetMapping("/allByOrder/{id}")
    public ResponseEntity<List<ProductDto>> getOrderProducts(@PathVariable Integer id) {
        return new ResponseEntity<>(productService.getOrderProducts(id), HttpStatus.OK);
    }
}
