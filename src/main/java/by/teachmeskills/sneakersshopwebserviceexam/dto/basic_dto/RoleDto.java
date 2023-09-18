package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleDto {
    @NotNull(message = "Id field in roleDto is null")
    @Min(value = 1, message = "Id field in roleDto less then 1")
    private Integer id;

    @NotNull(message = "Name field in roleDto is null")
    private String name;
}
