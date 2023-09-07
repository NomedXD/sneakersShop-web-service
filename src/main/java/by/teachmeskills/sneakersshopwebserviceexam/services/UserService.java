package by.teachmeskills.sneakersshopwebserviceexam.services;


import by.teachmeskills.sneakersshopwebserviceexam.domain.User;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.CategoryDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.OrderDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.LoginResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.RegistrationResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVExportException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.CSVImportException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    UserDto create(UserDto entity);

    List<UserDto> read();

    UserDto update(UserDto entity);

    void delete(Integer id);

    UserDto updateAccountData(UserDto updatedUserFields, UserDto userDto);

    ResponseEntity<LoginResponseWrapperDto> logIn(UserDto userDto);

    ResponseEntity<RegistrationResponseWrapperDto> register(UserDto userDto, String repeatPassword);

    Boolean checkIfLoggedInUser(UserDto userDto);

    User getUserById(Integer id);

    ResponseEntity<List<OrderDto>> getAccount(Integer userId, Integer currentPage, Integer pageSize);

    ResponseEntity<InputStreamResource> exportUserOrders(Integer userId) throws CSVExportException;

    ResponseEntity<List<OrderDto>> importUserOrders(MultipartFile file) throws CSVImportException;
}
