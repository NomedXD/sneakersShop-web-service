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
public class RegistrationRequestWrapperDto {

    @NotNull(message = "User field in registrationRequestWrapperDto is null")
    private UserDto user;

    @NotNull(message = "Repeated password field in registrationRequestWrapperDto is null")
    private String repeatPassword;
}
