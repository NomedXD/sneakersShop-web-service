package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.ImageDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
public class ProductDto {
    @NotNull(message = "Field is null validation error")
    private Integer id;

    @NotNull(message = "Field is null validation error")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Field does not satisfy regexp")
    @Size(max = 45, message = "Out of validation bounds")
    private String name;

    @NotNull(message = "Field is null validation error")
    private ImageDto image;

    @NotNull(message = "Field is null validation error")
    private String description;

    @NotNull(message = "Field is null validation error")
    private Integer categoryId;

    @NotNull(message = "Field is null validation error")
    @Positive(message = "Field must be positive")
    private float price;
}
