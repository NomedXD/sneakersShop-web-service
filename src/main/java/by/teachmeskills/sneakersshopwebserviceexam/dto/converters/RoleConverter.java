package by.teachmeskills.sneakersshopwebserviceexam.dto.converters;

import by.teachmeskills.sneakersshopwebserviceexam.domain.Role;
import by.teachmeskills.sneakersshopwebserviceexam.dto.basic_dto.RoleDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Data
@Component
public class RoleConverter {
    public RoleDto toDto(Role role) {
        return Optional.ofNullable(role).map(r -> RoleDto.builder()
                .id(r.getId())
                .name(r.getName()).build()).orElse(null);
    }

    public Role fromDto(RoleDto roleDto) {
        return Optional.ofNullable(roleDto).map(r -> Role.builder().
                        id(r.getId()).
                        name(r.getName()).
                        build()).
                orElse(null);
    }
}
