package by.teachmeskills.sneakersshopwebserviceexam.controllers;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CartDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.CheckoutRequestWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.ValidationException;
import by.teachmeskills.sneakersshopwebserviceexam.services.AuthService;
import by.teachmeskills.sneakersshopwebserviceexam.services.OrderService;
import by.teachmeskills.sneakersshopwebserviceexam.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Tag(name = "cart", description = "Cart Endpoints")
@RestController
@RequestMapping("/cart")
public class CartController {
    private final ProductService productService;
    private final OrderService orderService;
    private final AuthService authService;

    public CartController(ProductService productService, OrderService orderService, AuthService authService) {
        this.productService = productService;
        this.orderService = orderService;
        this.authService = authService;
    }

    @Operation(
            summary = "Remove product from cart",
            description = "Remove product from cart by product id",
            tags = {"cart"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product was removed",
                    content = @Content(schema = @Schema(implementation = CartDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Cart object validation error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<CartDto> deleteProductFromCart(@Valid @RequestBody CartDto cartDto, BindingResult result,
                                                         @PathVariable(name = "productId") Integer productId) {
        if (!result.hasErrors()) {
            cartDto.removeProduct(productId);
            return new ResponseEntity<>(cartDto, HttpStatus.OK);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @Operation(
            summary = "Add product to cart",
            description = "Add product to cart by product id",
            tags = {"cart"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product was added",
                    content = @Content(schema = @Schema(implementation = CartDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Cart object validation error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PutMapping("/{productId}")
    public ResponseEntity<CartDto> addProductToCart(@Valid @RequestBody CartDto cartDto, BindingResult result,
                                                    @PathVariable(name = "productId") Integer productId) {
        if (!result.hasErrors()) {
            cartDto.addProduct(productService.getProductById(productId));
            return new ResponseEntity<>(cartDto, HttpStatus.OK);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @Operation(
            summary = "Checkout products in cart",
            description = "Checkout all products in cart with order info",
            tags = {"cart"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful checkout",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Request cartDto object validation error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/checkout")
    public ResponseEntity<OrderDto> submitCheckout(@Valid @RequestBody CheckoutRequestWrapperDto checkoutRequestWrapperDto, BindingResult result) {
        if (!result.hasErrors()) {
            return orderService.applyOrder(checkoutRequestWrapperDto.getOrder(), checkoutRequestWrapperDto.getCart(), authService.getPrincipal().orElse(null));
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }
}
