package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {
    @NotNull(message = "Id field in orderDetailsDto is null")
    @Min(value = 1, message = "Id field in orderDetailsDto less then 1")
    private Integer id;

    @NotNull(message = "OrderId field in orderDetailsDto is null")
    @Min(value = 1, message = "OrderId field in orderDetailsDto less then 1")
    private Integer orderId;

    @NotNull(message = "ProductId field in orderDetailsDto is null")
    @Min(value = 1, message = "ProductId field in orderDetailsDto less then 1")
    private Integer productId;

    @NotNull(message = "ProductQuantity field in orderDetailsDto is null")
    @Min(value = 1, message = "ProductQuantity field in orderDetailsDto less then 1")
    private Integer productQuantity;
}
