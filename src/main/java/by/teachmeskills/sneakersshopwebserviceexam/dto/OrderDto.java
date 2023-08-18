package by.teachmeskills.sneakersshopwebserviceexam.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
public class OrderDto {
    @NotNull(message = "Field is null validation error")
    private Integer id;

    private Float price;

    private LocalDate date;

    private Integer userId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ProductDto> productList;

    private String creditCardNumber;

    private String shippingType;

    private Float shippingCost;

    private String code;

    private String address;

    private String customerNotes;
}
