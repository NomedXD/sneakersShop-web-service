package by.teachmeskills.sneakersshopwebserviceexam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseWrapperDto {
    private UserDto user;
    private List<CategoryDto> categories;
}
