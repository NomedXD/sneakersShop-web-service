package by.teachmeskills.sneakersshopwebserviceexam.services;

import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.JwtRequestDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.JwtResponseDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.AuthorizationException;
import lombok.NonNull;

import java.util.Optional;

public interface AuthService {
    JwtResponseDto login(@NonNull JwtRequestDto authRequest) throws AuthorizationException;
    JwtResponseDto getAccessToken(@NonNull String refreshToken) throws AuthorizationException;
    JwtResponseDto refresh(@NonNull String refreshToken) throws AuthorizationException;
    Optional<UserDto> getPrincipal();
}
