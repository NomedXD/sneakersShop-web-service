package by.teachmeskills.sneakersshopwebserviceexam.services;


import by.teachmeskills.sneakersshopwebserviceexam.domain.User;
import by.teachmeskills.sneakersshopwebserviceexam.dto.LoginResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.RegistrationResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    UserDto updateAccountData(UserDto updatedUserFields, UserDto userDto) throws EntityOperationException;

    ResponseEntity<LoginResponseWrapperDto> logIn(UserDto userDto) throws EntityOperationException;

    ResponseEntity<RegistrationResponseWrapperDto> register(UserDto userDto, String repeatPassword) throws EntityOperationException;

    ModelAndView checkIfLoggedInUser(User user) throws EntityOperationException;
    User getUserById(Integer id) throws EntityOperationException;
}
