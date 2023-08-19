package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
public class CategoryDto {
    private Integer id;

    @NotNull(message = "Field is null validation error")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Field does not satisfy regexp")
    private String name;

    private ImageDto image;

    @NotNull(message = "Field is null validation error")
    @Size(max = 45, message = "Out of validation bounds")
    private String sometext;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ProductDto> productList;
}
