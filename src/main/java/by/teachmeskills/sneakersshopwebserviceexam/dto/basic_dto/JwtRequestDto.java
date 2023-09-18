package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequestDto {
    @NotBlank(message = "Blank request email")
    private String mail;

    @NotBlank(message = "Blank request password")
    private String password;
}
