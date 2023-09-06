package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
public class SearchDto {
    @Size(max = 100, message = "Out of bounds searchDto search string")
    private String searchString;
    @Min(0)
    private Float priceFrom;
    @Min(0)
    private Float priceTo;
    @Size(max = 15)
    private String categoryName;
}
