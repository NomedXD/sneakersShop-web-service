package by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Updated user field in updateUserRequestWrapperDto is null")
    private UserDto updatedUserFields;

    @NotNull(message = "User field in updateUserRequestWrapperDto is null")
    private UserDto user;
}
