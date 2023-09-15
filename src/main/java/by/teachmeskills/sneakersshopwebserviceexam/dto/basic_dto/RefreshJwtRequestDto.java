package by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJwtRequestDto {
    @NotBlank(message = "RefreshToken field in refreshJwtRequestDto is blank")
    public String refreshToken;
}
