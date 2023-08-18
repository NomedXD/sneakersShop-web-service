package by.teachmeskills.sneakersshopwebserviceexam.services.impl;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Category;
import by.teachmeskills.sneakersshopwebserviceexam.domain.User;
import by.teachmeskills.sneakersshopwebserviceexam.dto.LoginResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.RegistrationResponseWrapperDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.CategoryConverter;
import by.teachmeskills.sneakersshopwebserviceexam.dto.converters.UserConverter;
import by.teachmeskills.sneakersshopwebserviceexam.enums.PagesPathEnum;
import by.teachmeskills.sneakersshopwebserviceexam.enums.RequestParamsEnum;
import by.teachmeskills.sneakersshopwebserviceexam.exception.EntityOperationException;
import by.teachmeskills.sneakersshopwebserviceexam.exception.NoSuchUserException;
import by.teachmeskills.sneakersshopwebserviceexam.repositories.UserRepository;
import by.teachmeskills.sneakersshopwebserviceexam.services.CategoryService;
import by.teachmeskills.sneakersshopwebserviceexam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final UserConverter userConverter;
    private final CategoryConverter categoryConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CategoryService categoryService, @Lazy UserConverter userConverter,
                           @Lazy CategoryConverter categoryConverter) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.userConverter = userConverter;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public User create(User entity) throws EntityOperationException {
        return userRepository.create(entity);
    }

    @Override
    public List<User> read() throws EntityOperationException {
        return userRepository.read();
    }

    @Override
    public User update(User entity) throws EntityOperationException {
        return userRepository.update(entity);
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        userRepository.delete(id);
    }

    @Override
    public UserDto updateAccountData(UserDto updatedUserFields, UserDto userDto) throws EntityOperationException {
        Map<String, String> params = new HashMap<>();
        params.put(RequestParamsEnum.MOBILE.getValue(), updatedUserFields.getMobile());
        params.put(RequestParamsEnum.STREET.getValue(), updatedUserFields.getStreet());
        params.put(RequestParamsEnum.ACCOMMODATION_NUMBER.getValue(), updatedUserFields.getAccommodationNumber());
        params.put(RequestParamsEnum.FLAT_NUMBER.getValue(), updatedUserFields.getFlatNumber());
        setInputs(params, userDto);
        updatedUserFields = UserDto.builder().id(userDto.getId()).mail(userDto.getMail()).password(userDto.getPassword()).
                name(userDto.getName()).surname(userDto.getSurname()).date(userDto.getDate()).currentBalance(userDto.getCurrentBalance()).
                mobile(params.get(RequestParamsEnum.MOBILE.getValue())).street(params.get(RequestParamsEnum.STREET.getValue())).
                accommodationNumber(params.get(RequestParamsEnum.ACCOMMODATION_NUMBER.getValue())).
                flatNumber(params.get(RequestParamsEnum.FLAT_NUMBER.getValue())).build();
        User dbUser = userConverter.fromDto(updatedUserFields);
        dbUser.setOrders(userRepository.getUserOrders(userDto.getId()));
        userDto = userConverter.toDto(update(dbUser));
        return userDto;
    }

    private void setInputs(Map<String, String> params, UserDto user) {
        for (var entry : params.entrySet()) {
            if (entry.getValue() == null || entry.getValue().isEmpty()) {
                switch (entry.getKey()) {
                    case "mobile" -> entry.setValue(user.getMobile());
                    case "street" -> entry.setValue(user.getStreet());
                    case "accommodationNumber" -> entry.setValue(user.getAccommodationNumber());
                    case "flatNumber" -> entry.setValue(user.getFlatNumber());
                }
            }
        }
    }

    @Override
    public ResponseEntity<LoginResponseWrapperDto> logIn(UserDto userDto) throws EntityOperationException {
        User loggedUser = userRepository.getUserByCredentials(userDto.getMail(), userDto.getPassword());
        if (loggedUser != null) {
            // Здесь было сохранение пользователя в сессию //
            return new ResponseEntity<>(new LoginResponseWrapperDto(userConverter.toDto(loggedUser),
                    categoryService.read().stream().map(categoryConverter::toDto).toList()), HttpStatus.OK);
        } else {
            throw new NoSuchUserException("Wrong email or password. Try again");
        }
    }

    @Override
    public ResponseEntity<RegistrationResponseWrapperDto> register(UserDto userDto, String repeatPassword) throws EntityOperationException {
//        if (!bindingResult.hasErrors() && ValidatorUtils.validatePasswordMatching(user.getPassword(), repeatPassword)) {
//
//        }`
        User user = userRepository.create(User.builder().mail(userDto.getMail()).password(userDto.getPassword()).name(userDto.getName()).
                surname(userDto.getSurname()).date(userDto.getDate()).currentBalance(0f).orders(new ArrayList<>()).build());
        return new ResponseEntity<>(new RegistrationResponseWrapperDto(userConverter.toDto(user),
                categoryService.read().stream().map(categoryConverter::toDto).toList()), HttpStatus.OK);
    }

    @Override
    public ModelAndView checkIfLoggedInUser(User user) throws EntityOperationException {
        ModelMap model = new ModelMap();
        if (user != null) {
            List<Category> categoriesList = categoryService.read();
            model.addAttribute(RequestParamsEnum.CATEGORIES.getValue(), categoriesList);
            return new ModelAndView(PagesPathEnum.SHOP_PAGE.getPath(), model);
        } else {
            return new ModelAndView(PagesPathEnum.LOG_IN_PAGE.getPath(), model);
        }
    }

    @Override
    public User getUserById(Integer id) throws EntityOperationException {
        return userRepository.getUserById(id);
    }
}