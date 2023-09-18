package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
public class DiscountCodeDto {

    @NotNull(message = "Name field in discountCodeDto is null")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Name field in discountCodeDto does not satisfy regexp")
    private String name;

    @Positive
    private Float discount;
}
