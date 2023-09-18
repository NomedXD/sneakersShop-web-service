package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import com.opencsv.bean.CsvBindByName;
import jakarta.validation.constraints.Min;
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

    @NotNull(message = "Id field in categoryDto is null")
    @Min(value = 1, message = "Id field in categoryDto less then 1")
    @CsvBindByName
    private Integer id;

    @NotNull(message = "Name field in categoryDto is null")
    @Pattern(regexp = "[a-zA-Z ,.'-]+", message = "Name field in categoryDto does not satisfy regexp")
    @CsvBindByName
    private String name;

    @NotNull(message = "ImageDtoList field in categoryDro is null")
    private List<ImageDto> imageDtoList;

    @NotNull(message = "Some text field in categoryDto is null")
    @Size(max = 45, message = "Out of bounds categoryDto some text")
    @CsvBindByName
    private String sometext;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ProductDto> productList;
}
