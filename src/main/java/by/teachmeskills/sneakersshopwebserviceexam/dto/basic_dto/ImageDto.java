package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
public class ImageDto {
    @NotNull(message = "Field is null validation error")
    private Integer id;

    @NotNull(message = "Field is null validation error")
    @Size(max = 45, message = "Out of validation bounds")
    private String path;
}
