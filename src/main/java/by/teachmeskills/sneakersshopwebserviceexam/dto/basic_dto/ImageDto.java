package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class ImageDto {

    @NotNull(message = "Id field in imageDto is null")
    @Min(value = 1, message = "Id field in imageDto less then 1")
    private Integer id;

    @NotNull(message = "Path field in imageDto is null")
    @Size(max = 45, message = "Out of bounds imageDto path")
    private String path;

    @Nullable
    private Boolean isPrime;
}
