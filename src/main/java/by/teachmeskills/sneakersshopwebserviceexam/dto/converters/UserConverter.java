package by.teachmeskills.sneakersshopwebserviceexam.dto.converters;

import by.teachmeskills.sneakersshopwebserviceexam.domain.User;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.UserDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Data
public class UserConverter {

    private final OrderConverter orderConverter;
    private final RoleConverter roleConverter;

    @Autowired
    public UserConverter(OrderConverter orderConverter, RoleConverter roleConverter) {
        this.orderConverter = orderConverter;
        this.roleConverter = roleConverter;
    }

    public UserDto toDto(User user) {
        return Optional.ofNullable(user).map(u -> UserDto.builder().id(u.getId()).mail(u.getMail()).password(u.getPassword()).
                name(u.getName()).surname(u.getSurname()).date(u.getDate()).mobile(u.getMobile()).street(u.getStreet()).
                accommodationNumber(u.getAccommodationNumber()).flatNumber(u.getFlatNumber()).
                orders(Optional.ofNullable(u.getOrders()).map(orders -> orders.stream().map(orderConverter::toDto).
                        toList()).orElse(List.of())).
                roles(Optional.ofNullable(u.getRoles()).map(roles -> roles.stream().map(roleConverter::toDto).
                        collect(Collectors.toList())).orElse(List.of())).build()).orElse(null);
    }

    public User fromDto(UserDto userDto) {
        return Optional.ofNullable(userDto).map(u -> User.builder().id(u.getId()).mail(u.getMail()).password(u.getPassword()).
                name(u.getName()).surname(u.getSurname()).date(u.getDate()).mobile(u.getMobile()).street(u.getStreet()).
                accommodationNumber(u.getAccommodationNumber()).flatNumber(u.getFlatNumber()).
                orders(Optional.ofNullable(u.getOrders()).map(orders -> orders.stream().map(orderConverter::fromDto).
                        toList()).orElse(List.of())).roles(Optional.ofNullable(u.getRoles()).map(roles -> roles.stream().map(roleConverter::fromDto).collect(Collectors.toList())).orElse(List.of())).build()).orElse(null);
    }
}
