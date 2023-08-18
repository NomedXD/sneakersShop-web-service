package by.teachmeskills.sneakersshopwebserviceexam.dto;

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
public class UpdateUserRequestWrapperDto {
    private UserDto updatedUserFields;
    private UserDto user;
}
