package by.teachmeskills.sneakersshopwebserviceexam.dto.converters;

import by.teachmeskills.sneakersshopwebserviceexam.domain.User;
import by.teachmeskills.sneakersshopwebserviceexam.dto.UserDto;
import by.teachmeskills.sneakersshopwebserviceexam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    private final UserService userService;

    @Autowired
    public UserConverter(UserService userService) {
        this.userService = userService;
    }

    public UserDto toDto(User user) {
        return UserDto.builder().id(user.getId()).mail(user.getMail()).password(user.getPassword()).
                name(user.getName()).surname(user.getSurname()).date(user.getDate()).
                currentBalance(user.getCurrentBalance()).mobile(user.getMobile()).street(user.getStreet()).
                accommodationNumber(user.getAccommodationNumber()).flatNumber(user.getFlatNumber()).
                orders(user.getOrders()).build();
    }

    public User fromDto(UserDto userDto) {
        return User.builder().id(userDto.getId()).mail(userDto.getMail()).password(userDto.getPassword()).
                name(userDto.getName()).surname(userDto.getSurname()).date(userDto.getDate()).
                currentBalance(userDto.getCurrentBalance()).mobile(userDto.getMobile()).street(userDto.getStreet()).
                accommodationNumber(userDto.getAccommodationNumber()).flatNumber(userDto.getFlatNumber()).
                orders(userService.getUserOrders(userDto.getId())).build();
    }
}
