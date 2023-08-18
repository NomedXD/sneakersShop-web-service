package by.teachmeskills.sneakersshopwebserviceexam.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
public class UpdateUserWrapperDto {
    private UserDto updatedUserFields;
    private UserDto user;
}
