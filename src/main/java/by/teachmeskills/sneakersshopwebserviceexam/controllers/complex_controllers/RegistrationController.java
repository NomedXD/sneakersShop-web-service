package by.teachmeskills.sneakersshopwebserviceexam.controllers.complex_controllers;

import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.RegistrationRequestWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.complex_wrappwer_dto.RegistrationResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import by.teachmeskills.sneakersshopwebserviceexam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    // Здесь и в других контроллерах можно сделать один wrapperDto, но тогда не все поля будут использоваться => больше размер пакета
    @PostMapping
    public ResponseEntity<RegistrationResponseWrapperDto> register(@RequestBody RegistrationRequestWrapperDto requestBody) throws EntityOperationException {
        return userService.register(requestBody.getUser(), requestBody.getRepeatPassword());
    }
}
