package by.teachmeskills.sneakersshopwebserviceexam.controllers;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVExportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVImportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.ValidationException;
import by.teachmeskills.sneakersshopwebserviceexam.services.OrderService;
import by.teachmeskills.sneakersshopwebserviceexam.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
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

@Tag(name = "order", description = "Order Endpoints")
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @Operation(
            summary = "Create order",
            description = "Create order",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order was created",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Order object validation error - server error"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto, BindingResult result) {
        if (!result.hasErrors()) {
            return new ResponseEntity<>(orderService.create(orderDto), HttpStatus.CREATED);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @Operation(
            summary = "Get all orders",
            description = "Get all orders",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orders were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return new ResponseEntity<>(orderService.read(), HttpStatus.OK);
    }

    @Operation(
            summary = "Update order",
            description = "Update order",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order was updated",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Order object validation error - server error"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @PutMapping
    public ResponseEntity<OrderDto> updateOrder(@Valid @RequestBody OrderDto orderDto, BindingResult result) {
        if (!result.hasErrors()) {
            return new ResponseEntity<>(orderService.update(orderDto), HttpStatus.OK);
        } else {
            throw new ValidationException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
    }

    @Operation(
            summary = "Remove order",
            description = "Remove order by it's id",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order was deleted"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @DeleteMapping("/remove/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        orderService.delete(id);
    }

    @Operation(
            summary = "Get order",
            description = "Get order by it's id",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order were found",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order were not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Integer id) {
        return Optional.ofNullable(orderService.getOrderById(id))
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Get orders",
            description = "Get orders by user id",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Database error - server error"
            )
    })
    @GetMapping("/allByUser/{id}")
    public ResponseEntity<List<OrderDto>> getUserOrders(@PathVariable Integer id) {
        return new ResponseEntity<>(orderService.getUserOrders(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Export orders",
            description = "Export user orders to csv file by user id. Products export is separated to another file",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orders were exported"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "CSVExportException was thrown - server error"
            )
    })
    @GetMapping("/export/{userId}")
    public ResponseEntity<InputStreamResource> exportUserOrders(@PathVariable Integer userId) throws CSVExportException {
        return userService.exportUserOrders(userId);
    }

    @Operation(
            summary = "Import orders",
            description = "Import user orders from csv file. Products import executes from another file",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orders were imported and created in database"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "CSVImportException was thrown - server error"
            )
    })
    @PostMapping("/import")
    public ResponseEntity<List<OrderDto>> importUserOrders(@RequestParam("file") MultipartFile file) throws CSVImportException {
        return userService.importUserOrders(file);
    }
}
