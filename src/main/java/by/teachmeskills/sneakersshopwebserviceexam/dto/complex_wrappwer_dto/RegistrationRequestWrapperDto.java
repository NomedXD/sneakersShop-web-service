package by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
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
    private UserDto user;
    private String repeatPassword;
}
