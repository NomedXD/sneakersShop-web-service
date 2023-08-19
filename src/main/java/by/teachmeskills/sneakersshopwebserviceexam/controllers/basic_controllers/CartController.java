package by.teachmeskills.sneakersshopwebserviceexam.controllers.basic_controllers;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CartDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.CheckoutRequestResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import by.teachmeskills.sneakersshopwebserviceexam.services.OrderService;
import by.teachmeskills.sneakersshopwebserviceexam.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final ProductService productService;
    private final OrderService orderService;

    public CartController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<CartDto> deleteProductFromCart(@PathVariable(name = "productId") Integer productId,
                                              @RequestBody CartDto cartDto) {
        cartDto.removeProduct(productId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @PutMapping("/add/{productId}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable(name = "productId") Integer productId,
                                                    @RequestBody CartDto cartDto) throws EntityOperationException {
        cartDto.addProduct(productService.getProductById(productId));
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutRequestResponseWrapperDto> submitCheckout(@RequestBody CheckoutRequestResponseWrapperDto requestBody) throws EntityOperationException {
        return orderService.applyOrder(requestBody.getOrder(), requestBody.getCart(), requestBody.getUser());
    }
    /*
        @PostMapping("/apply_quantity")
    public ModelAndView applyQuantity(@SessionAttribute(name = EshopConstants.SHOPPING_CART) Cart cart, HttpServletRequest request) {
        return productService.applyProductsQuantity(cart, request);
    }
     */
}
