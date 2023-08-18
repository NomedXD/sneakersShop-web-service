package by.teachmeskills.sneakersshopwebserviceexam.dto.converters;

import by.teachmeskills.sneakersshopwebserviceexam.domain.User;
import by.teachmeskills.sneakersshopwebserviceexam.dto.UserDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Data
public class UserConverter {

    private final OrderConverter orderConverter;

    @Autowired
    public UserConverter(OrderConverter orderConverter) {
        this.orderConverter = orderConverter;
    }

    public UserDto toDto(User user) {
        return Optional.ofNullable(user).map(u -> UserDto.builder().id(u.getId()).mail(u.getMail()).password(u.getPassword()).
                name(u.getName()).surname(u.getSurname()).date(u.getDate()).
                currentBalance(u.getCurrentBalance()).mobile(u.getMobile()).street(u.getStreet()).
                accommodationNumber(u.getAccommodationNumber()).flatNumber(u.getFlatNumber()).
                orders(Optional.ofNullable(u.getOrders()).map(orders -> orders.stream().map(orderConverter::toDto).
                        toList()).orElse(List.of())).build()).orElse(null);
    }

    public User fromDto(UserDto userDto) {
        return Optional.ofNullable(userDto).map(u -> User.builder().id(u.getId()).mail(u.getMail()).password(u.getPassword()).
                name(u.getName()).surname(u.getSurname()).date(u.getDate()).
                currentBalance(u.getCurrentBalance()).mobile(u.getMobile()).street(u.getStreet()).
                accommodationNumber(u.getAccommodationNumber()).flatNumber(u.getFlatNumber()).
                orders(Optional.ofNullable(u.getOrders()).map(orders -> orders.stream().map(orderConverter::fromDto).
                        toList()).orElse(List.of())).build()).orElse(null);
    }
}
