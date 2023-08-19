package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import jakarta.validation.constraints.Min;
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

    @NotNull(message = "Id field in productDto is null")
    @Min(value = 1, message = "Id field in productDto less then 1")
    private Integer id;

    @NotNull(message = "Name field in productDto is null")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Name field in productDto does not satisfy regexp")
    @Size(max = 45, message = "Out of bounds productDto name")
    private String name;

    @NotNull(message = "Image field in productDto is null")
    private ImageDto image;

    @NotNull(message = "Description field in productDto is null")
    @Size(max = 2000, message = "Out of bounds productDto description")
    private String description;

    @NotNull(message = "Category id field in productDto is null")
    @Positive(message = "Category id in productDto must be positive")
    private Integer categoryId;

    @NotNull(message = "Price field in productDto is null")
    @Positive(message = "Price in productDto must be positive")
    private Float price;
}
