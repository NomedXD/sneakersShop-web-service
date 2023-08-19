package by.teachmeskills.sneakersshopwebserviceexam.services;


import by.teachmeskills.sneakersshopwebserviceexam.domain.User;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CategoryDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.LoginResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.RegistrationResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserDto create(UserDto entity) throws EntityOperationException;

    List<UserDto> read() throws EntityOperationException;

    UserDto update(UserDto entity) throws EntityOperationException;

    void delete(Integer id) throws EntityOperationException;
    UserDto updateAccountData(UserDto updatedUserFields, UserDto userDto) throws EntityOperationException;

    ResponseEntity<LoginResponseWrapperDto> logIn(UserDto userDto) throws EntityOperationException;

    ResponseEntity<RegistrationResponseWrapperDto> register(UserDto userDto, String repeatPassword) throws EntityOperationException;

    ResponseEntity<List<CategoryDto>> checkIfLoggedInUser(UserDto userDto) throws EntityOperationException;
    User getUserById(Integer id) throws EntityOperationException;
}
